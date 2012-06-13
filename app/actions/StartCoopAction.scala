package actions


import anorm._
import models.Coop
import models.CoopForm
import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import models.Coop

object StartCoopAction extends Controller {

  val form: Form[Coop] = CoopForm.form

  def actionForm = Action { implicit request =>
      Ok(html.startcoop(form)) 
  }


  def submitForm = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.startcoop(formWithErrors)),
      item => {
        item.save
	Redirect(controllers.routes.Application.index)
      }
    )}

}



