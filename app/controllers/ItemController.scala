package controllers

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
import anorm.SqlParser._
import views._

import models.Item
import helpers.CustomFormats._

object ItemController extends Controller {

  val form: Form[Item] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> text,
      "description" -> text,
      "cost" -> money,
      "url" -> text,
    "created_by_id" -> number)
    (Item.apply)(Item.unapply))

  def index = Action { implicit request =>
    Ok("views.html.index()")
  }

  def list = Action {
    Ok(html.admin.item.list(Item.all))
  }

  def showItem(id: Long) = Action {
    val option = Item.findById(id)
    option match {
      case None => Ok("no item exists")
      case Some(item) => {
        Ok(html.admin.item.showItem(item))
      }
    }    
  }

  def newItem = Action {
    Ok(html.admin.item.newItem(form))
  }

  def saveItem = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.admin.item.newItem(formWithErrors)),
      item => {
        Item.save(item)
        Ok(html.admin.item.showItem(item))
      }
      )}


  def test(id: Long) = Action { Ok("hello") }

  def editItem(id: Long) = Action {
    Item.findById(id).map { item =>
      Ok(html.admin.item.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.admin.item.editItem(id, formWithErrors)),
      item => {
        Item.update(id, item)
        Ok("saved...")
      }
    )
  }


}
