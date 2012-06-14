package actions

import models.Coop
import models.Member
import models.ItemOrder
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object RemoveMemberFromItemOrderAction extends Controller with Secured {

  def action(coopId:Long, memberId: Long, id: Long) = Action 
  { implicit request =>
    val itemOpt = ItemOrder.findById(id)
   val memberOpt = Member.findById(memberId)

   ItemOrder.removeMember(id, memberOpt.get)
   Ok(html.showitemorder(itemOpt.get))
  }
}
