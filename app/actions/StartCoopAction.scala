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

object StartCoopAction extends Controller {

  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "coop_type_id" -> number,
      "manager_id" -> number
    )(Coop.apply)(Coop.unapply))


  def actionForm = Action {
      Ok(html.admin.coop.newItem(form)) 
  }


}


