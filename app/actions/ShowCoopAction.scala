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

object ShowCoopAction extends Controller {
  
  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "coop_type_id" -> number,
      "manager_id" -> number,
      "zip_code" -> nonEmptyText
    )(Coop.apply)(Coop.unapply))

  def showItem(id: Long) = Action { implicit request =>
      val itemOption = Coop.findById(id)
      itemOption match {
      case None => Ok("no item exists")
      case Some(item) => {
        Ok(html.showcoop(item))
      }
      }
    }    
}
