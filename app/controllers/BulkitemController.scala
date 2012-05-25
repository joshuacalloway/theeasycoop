package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import views._

import models.Bulkitem

object BulkitemController extends Controller {

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
}
