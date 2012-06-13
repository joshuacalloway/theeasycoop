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
import helpers.Utils

object AddLoggedInUserToItemOrderAction extends Controller with Secured {


  def action(coopId: Long, id: Long) = Action 
  { implicit request =>
    val member = Utils.getLoggedInUser(request.session)
					       
   ItemOrder.addMember(id, member)
   Ok(html.showitemorder(ItemOrder.findById(id).get))
 }
}
