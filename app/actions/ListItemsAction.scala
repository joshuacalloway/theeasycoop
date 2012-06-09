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

import models.Item
import helpers.CustomFormats._
import models.Coop

object ListItemsAction extends Controller with Secured {
  
  def list = Action { implicit request =>
    Ok(html.listitems(Item.all))
  }
}
