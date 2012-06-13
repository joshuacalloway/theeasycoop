package actions


import anorm._
import models.Coop
import models.Member
import models.MemberForm
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import models.Coop

object RegisterMemberAction extends Controller {
  val form: Form[Member] = MemberForm.form

  def actionForm = Action { implicit request =>
      Ok(html.registermember(form)) 
  }

  def submitForm = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.registermember(formWithErrors)),
      item => {
	item.save
        Redirect(controllers.routes.Application.index).withSession("username" -> item.email)
      }
     )
			 }
}



