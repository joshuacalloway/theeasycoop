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

import models.ItemOrder
import helpers.CustomFormats._
import models.Coop

object EditItemOrderInCoopAction extends Controller with Secured {

  val form: Form[ItemOrder] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "bulkitem_id" -> number,
      "minimumbuyers" -> number,
      "itemcost" -> money,
      "itemdescription" -> text,
      "deadline_by" -> date("yyyy-MM-dd"),
      "deliveryaddress" -> text,
      "created_at" -> date("yyyy-MM-dd"),
      "created_by_id" -> number,
      "coop_id" -> number
    )(ItemOrder.apply)(ItemOrder.unapply))


  def editItem(coopId: Long, id: Long) = Action { implicit request =>
    val coop = Coop.findById(coopId).get
    val item = ItemOrder.findById(id).get
    if (item.coop.id == coop.id) {
      Ok(html.editItemOrderInCoop(coop.id.get, id, form.fill(ItemOrder.findById(id).get)))
    } else NotFound
  }

  def updateItem(coopId: Long, id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => Ok("BadRequest(view.editItem(id, formWithErrors))"),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
        Ok(html.admin.itemorder.list(ItemOrder.findByCoopId(coopId)))
      }
    )
  }

}
