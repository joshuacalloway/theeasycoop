package actions

import anorm._
import models.Item
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import helpers.CustomFormats._

object EditItemAction extends Controller with Secured {
  val form: Form[Item] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> text,
      "description" -> text,
      "cost" -> money,
      "url" -> text,
    "created_by_id" -> number)
    (Item.apply)(Item.unapply))


  def editItem(id: Long) = Action { implicit request => 
    Item.findById(id) match { 
      case Some(item) =>
        Ok(html.edititem(id, form.fill(item)))
      case _ => Ok("error")
    }
  }


  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.edititem(id, formWithErrors)),
      item => {
        item.update(id)
	Redirect(controllers.routes.Application.index)
      }
    )
				   }

}
