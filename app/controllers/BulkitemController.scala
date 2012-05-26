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
    Ok("listing bulkitems")
  }

  def show(id: Long) = Action {
    val option = Bulkitem.findById(id)
    option match {
      case None => Ok("no bulkitem exists")
      case Some(item) => {
        Ok(html.bulkitem.show(item))
      }
    }    
  }

  def newRecord = Action {
    Ok(html.bulkitem.newRecord(form))
  }

  def saveRecord = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.bulkitem.newRecord(formWithErrors)),
      bulkitem => {
        Bulkitem.save(bulkitem)
        Ok(html.bulkitem.show(bulkitem))
      }
      )}
}
