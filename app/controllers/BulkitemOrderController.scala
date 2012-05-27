package controllers

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
import actions.Secured


object BulkitemOrderController extends AbstractCRUDController with Secured {
  override type ModelType = BulkitemOrder

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


  override protected def model_all() = BulkitemOrder.all
  override protected def model_findById(id: Long) = BulkitemOrder.findById(id)
  override protected def model_delete(id: Long) = BulkitemOrder.delete(id)
  override protected def listView = views.html.bulkitemorder.list(model_all)
  
  // def list = Action {
  //   Ok(html.bulkitemorder.list(BulkitemOrder.all))
  // }

 def newItem = IsAuthenticated { _ => _ =>
      Ok(html.bulkitemorder.newItem(form)) 
                              }

  def editItem(id: Long) = Action {
    BulkitemOrder.findById(id).map { item =>
      Ok(html.bulkitemorder.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

  override def saveItem: play.api.mvc.Action[play.api.mvc.AnyContent] = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.bulkitemorder.newItem(formWithErrors)),
      item => {
        item.save
        Ok(listView)
      }
    )}

  // def updateItem(id: Long) = Action { implicit request =>
  //   form.bindFromRequest.fold(
  //     formWithErrors => BadRequest(html.bulkitemorder.editItem(id, formWithErrors)),
  //     item => {
  //       BulkitemOrder.update(id, item)
  //       Ok("saved...")
  //     }
  //   )
  // }


}
