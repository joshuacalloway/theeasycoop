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

import models.BulkitemOrder
import helpers.CustomFormats._
import models.Coop

object ListMembersInBulkitemOrderInCoopAction extends Controller with Secured {
  
  def list(coopId:Long, id: Long) = Action {
    //Ok("")
    Ok(html.public.actions.listMembersInBulkitemOrderInCoop(BulkitemOrder.findById(id).get.members))
  }

}
