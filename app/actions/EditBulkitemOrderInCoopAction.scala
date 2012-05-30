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

import models.BulkitemOrder
import helpers.CustomFormats._
import models.Coop

object EditBulkitemOrderInCoopAction extends Controller with Secured {

  val form: Form[BulkitemOrder] = Form(
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
    )(BulkitemOrder.apply)(BulkitemOrder.unapply))


  def editItem(coopId: Long, id: Long) = Action {
    val coop = Coop.findById(coopId).get
    val item = BulkitemOrder.findById(id).get
    if (item.coop.id == coop.id) {
      Ok(html.public.actions.editBulkitemOrderInCoop(coop.id.get, id, form.fill(BulkitemOrder.findById(id).get)))
    } else NotFound
  }

  def updateItem(coopId: Long, id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => Ok("BadRequest(view.editItem(id, formWithErrors))"),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
        Ok(html.bulkitemorder.list(BulkitemOrder.findByCoopId(coopId)))
      }
    )
  }

}
