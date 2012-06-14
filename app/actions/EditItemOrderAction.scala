package actions

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
//import anorm.SqlParser._
import views._

import models.{ItemOrder, ItemOrderForm}
import helpers.CustomFormats._
import models.Coop

object EditItemOrderAction extends Controller with Secured {

  val form: Form[ItemOrder] = ItemOrderForm.form

  def editItem(coopId: Long, id: Long) = Action { implicit request =>
    val coop = Coop.findById(coopId).get
    val item = ItemOrder.findById(id).get
    if (item.coop.id == coop.id) {
      Ok(html.edititemorder(coop.id.get, id, form.fill(ItemOrder.findById(id).get)))
    } else NotFound
  }

  def updateItem(coopId: Long, id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.edititemorder(coopId, id, formWithErrors)),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
        Ok(html.admin.itemorder.list(ItemOrder.findByCoopId(coopId)))
      }
    )
  }

}
