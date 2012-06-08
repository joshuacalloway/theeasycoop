package actions

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
import views._

import models.Item
import helpers.CustomFormats._
import models.Coop

object AddItemAction extends Controller with Secured {
  val form: Form[Item] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> text,
      "description" -> text,
      "cost" -> money,
      "url" -> text)
    (Item.apply)(Item.unapply))

  def saveItem = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.admin.item.newItem(formWithErrors)),
      item => {
        Item.save(item)
        Redirect(controllers.routes.Application.index)
      }
      )}

  def newItem = Action { implicit request =>
    Ok(html.additem(form))
  }
}
