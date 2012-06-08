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

import models.ItemOrder
import helpers.CustomFormats._
import models.Coop

object AddItemOrderToCoopAction extends Controller with Secured {
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

  def newItemByCoop(id: Long) = Action { implicit request =>
    Ok(html.additemorderbycoop(Coop.findById(id).get, form)) 
                                               }

  def saveItemByCoop(id: Long) = Action 
  { implicit request =>
    Logger.info("saveItemByCoop.... id : " + id)
   form.bindFromRequest.fold(
     formWithErrors => BadRequest(html.additemorderbycoop(Coop.findById(id).get, formWithErrors)),
     item => {
       item.save
       Redirect(actions.routes.ListItemOrdersByCoopAction.listByCoop(id))
     }
   )}
}
