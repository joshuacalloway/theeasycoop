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
      "url" -> text)
    (Item.apply)(Item.unapply))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok(html.item.list(Item.all))
  }

  def showItem(id: Long) = Action {
    val option = Item.findById(id)
    option match {
      case None => Ok("no item exists")
      case Some(item) => {
        Ok(html.item.showItem(item))
      }
    }    
  }

  def newItem = Action {
    Ok(html.item.newItem(form))
  }

  def saveItem = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.item.newItem(formWithErrors)),
      item => {
        Item.save(item)
        Ok(html.item.showItem(item))
      }
      )}


  def editItem(id: Long) = Action {
    Item.findById(id).map { item =>
      Ok(html.item.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.item.editItem(id, formWithErrors)),
      item => {
        Item.update(id, item)
        Ok("saved...")
      }
    )
  }


}
