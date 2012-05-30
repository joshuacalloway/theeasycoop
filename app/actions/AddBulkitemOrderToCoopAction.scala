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

import models.BulkitemOrder
import helpers.CustomFormats._
import models.Coop

object AddBulkitemOrderToCoopAction extends Controller with Secured {
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

  def newItemByCoop(id: Long) = IsAuthenticated { _ => _ =>
    Ok(html.public.actions.newItemByCoop(Coop.findById(id).get, form)) 
                                               }

  def saveItemByCoop(id: Long) = Action 
  { implicit request =>
    Logger.info("saveItemByCoop.... id : " + id)
   form.bindFromRequest.fold(
     formWithErrors => BadRequest(html.public.actions.newItemByCoop(Coop.findById(id).get, formWithErrors)),
     item => {
       item.save
       Redirect(actions.routes.ListBulkitemOrdersByCoopAction.listByCoop(id))
     }
   )}
}
