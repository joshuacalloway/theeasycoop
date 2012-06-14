package actions

import anorm._
import models.Member
import models.Coop
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object UnSuspendMemberAction extends Controller with Secured {

  def action(coopId:Long, id: Long) = Action { implicit request => 
    (Member.findById(id), Coop.findById(coopId)) match { 
      case (Some(m), Some(c)) => {
	c.unSuspendMember(m)
        Ok(html.showmember(m))
      }
      case _ => Ok("error")
    }
  }

}
