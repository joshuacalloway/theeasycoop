package actions


import anorm._
import models.Coop
import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import models.Coop

object RegisterMemberAction extends Controller {
  val form: Form[Member] = Form (
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "email" -> optional(text),
      "password" -> nonEmptyText,
      "cell" -> optional(text),
      "address" -> text)(Member.apply)(Member.unapply))


  def actionForm = Action {
      Ok(html.admin.member.newItem(form)) 
  }



  def submitForm = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.admin.member.newItem(formWithErrors)),
      item => {
	item.save
	Ok("item saved")
      }
     )
			 }
}



