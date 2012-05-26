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

import models.Bulkitem
import helpers.CustomFormats._

object BulkitemController extends Controller {

  val form: Form[Bulkitem] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> text,
      "description" -> text,
      "cost" -> money,
      "url" -> text)
    (Bulkitem.apply)(Bulkitem.unapply))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok(html.bulkitem.list(Bulkitem.all))
  }

  def showItem(id: Long) = Action {
    val option = Bulkitem.findById(id)
    option match {
      case None => Ok("no bulkitem exists")
      case Some(item) => {
        Ok(html.bulkitem.showItem(item))
      }
    }    
  }

  def newItem = Action {
    Ok(html.bulkitem.newItem(form))
  }

  def saveItem = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.bulkitem.newItem(formWithErrors)),
      bulkitem => {
        Bulkitem.save(bulkitem)
        Ok(html.bulkitem.showItem(bulkitem))
      }
      )}


  def editItem(id: Long) = Action {
    Bulkitem.findById(id).map { item =>
      Ok(html.bulkitem.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.bulkitem.editItem(id, formWithErrors)),
      item => {
        Bulkitem.update(id, item)
        Ok("saved...")
      }
    )
  }


}
