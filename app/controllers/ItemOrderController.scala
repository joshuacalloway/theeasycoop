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

import models.ItemOrder
import helpers.CustomFormats._
import actions.Secured
import models.Coop

object ItemOrderController extends AbstractCRUDController with Secured {
  override type ModelType = ItemOrder

  val form: Form[ItemOrder] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "item_id" -> number,
      "minimumbuyers" -> number,
      "membercost" -> money,
      "description" -> text,
      "deadline_by" -> date("yyyy-MM-dd"),
      "deliveryaddress" -> text,
      "created_at" -> date("yyyy-MM-dd"),
      "created_by_id" -> number,
      "coop_id" -> number
    )(ItemOrder.apply)(ItemOrder.unapply))


  override protected def model_all() = ItemOrder.all
  override protected def model_findById(id: Long) = ItemOrder.findById(id)
  override protected def model_delete(id: Long) = ItemOrder.delete(id)
  override protected def listView = views.html.itemorder.list(model_all)
  
  def listByCoop(id: Long) = Action {
    Ok(html.itemorder.list(ItemOrder.findByCoopId(id)))
  }

  def newItem = IsAuthenticated { _ => _ =>
      Ok(html.itemorder.newItem(form)) 
                              }

  def editItem(id: Long) = IsAuthenticated { _ => _ =>
    ItemOrder.findById(id).map { item =>
      Ok(html.itemorder.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

  override def saveItem: play.api.mvc.Action[play.api.mvc.AnyContent] = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.itemorder.newItem(formWithErrors)),
      item => {
        item.save
        Ok(listView)
      }
    )}

}
