package actions

import anorm._
import models.Item
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import helpers.CustomFormats._
import models.ItemForm

object EditItemAction extends Controller with Secured {
  val form: Form[Item] = ItemForm.form

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
