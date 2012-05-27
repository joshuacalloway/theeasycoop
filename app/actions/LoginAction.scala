package actions

import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object LoginAction extends Controller {

  // -- Authentication

  val form = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => Member.authenticate(email, password).isDefined
    })
  )

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    Ok(html.login(form))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      member => 
        Redirect(controllers.routes.Application.index).withSession("email" -> member._1)
    )
  }

  /**
   * Logout and clean the session.
   */
  def logout = Action {
    Redirect(routes.LoginAction.login).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

}

/**
 * Provide security features
 */
trait Secured {
  
  /**
   * Retrieve the connected user email.
   */
  private def username(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(actions.routes.LoginAction.login)
  
  // --
  
  /** 
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }

  // /**
  //  * Check if the connected user is a member of this project.
  //  */
  // def IsMemberOf(project: Long)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
  //   if(Project.isMember(project, user)) {
  //     f(user)(request)
  //   } else {
  //     Results.Forbidden
  //   }
  // }

  // /**
  //  * Check if the connected user is a owner of this task.
  //  */
  // def IsOwnerOf(task: Long)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
  //   if(Task.isOwner(task, user)) {
  //     f(user)(request)
  //   } else {
  //     Results.Forbidden
  //   }
  // }

}

