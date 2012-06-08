package actions

import anorm._
import models.Member
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object EditMemberAction extends Controller with Secured {
  val form: Form[Member] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "cell" -> optional(text),
      "address" -> text)(Member.apply)(Member.unapply))


  def editItem(id: Long) = Action { implicit request => 
    Member.findById(id) match { 
      case Some(item) =>
        Ok(html.editmember(id, form.fill(item)))
      case _ => Ok("error")
    }
  }


  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.editmember(id, formWithErrors)),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
	Redirect(controllers.routes.Application.index)
      }
    )
				   }

}
