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

import models.{ItemOrder, ItemOrderForm}
import helpers.CustomFormats._
import actions.Secured
import models.Coop

object ItemOrderController extends AbstractCRUDController with Secured {
  override type ModelType = ItemOrder

  val form: Form[ItemOrder] = ItemOrderForm.form

  override protected def model_all() = ItemOrder.all
  override protected def model_findById(id: Long) = ItemOrder.findById(id)
  override protected def model_delete(id: Long) = ItemOrder.delete(id)
  override protected def listView = views.html.admin.itemorder.list(model_all)
  
  def listByCoop(id: Pk[Long]) = Action {
    Ok(html.admin.itemorder.list(ItemOrder.findByCoopId(id)))
  }

  def newItem = Action {
    Ok(html.admin.itemorder.newItem(form)) 
  }

  def editItem(id: Long) = Action {
    ItemOrder.findById(id).map { item =>
      Ok("html.admin.itemorder.editItem(id, form.fill(item))")
                         }.getOrElse(NotFound)
  }

  override def saveItem: play.api.mvc.Action[play.api.mvc.AnyContent] = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.admin.itemorder.newItem(formWithErrors)),
      item => {
        item.save
        Ok(listView)
      }
    )}

}
