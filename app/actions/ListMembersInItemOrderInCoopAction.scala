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

object ListMembersInItemOrderInCoopAction extends Controller with Secured {
  
  def list(coopId:Long, id: Long) = Action { implicit request =>
    Ok(html.listmembersinitemorderincoop(ItemOrder.findById(id).get.members))
  }

}
