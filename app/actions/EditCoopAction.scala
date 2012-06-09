package actions

import anorm._
import models.Coop
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object EditCoopAction extends Controller with Secured {
  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "coop_type_id" -> number,
      "manager_id" -> number
    )(Coop.apply)(Coop.unapply))


  def editItem(id: Long) = Action { implicit request => 
    Coop.findById(id) match { 
      case Some(item) =>
        Ok(html.editcoop(id, form.fill(item)))
      case _ => Ok("error")
    }
  }


  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.editcoop(id, formWithErrors)),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
	Redirect(controllers.routes.Application.index)
      }
    )
				   }

}
