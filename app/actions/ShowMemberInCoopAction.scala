package actions

import anorm._
import models.Coop
import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowMemberInCoopAction extends Controller with Secured {
  def showItem(coopId: Long, memberId: Long) : play.api.mvc.Action[play.api.mvc.AnyContent] =
    Action {
      val coopOpt = Coop.findById(coopId)
      val memberOpt = Member.findById(memberId)
      (coopOpt, memberOpt) match {
        case (Some(coop), Some(member)) if coop.isMember(member) => Ok("member exists")
        case _ => Ok("member does not belong in coop")
      }
    }    

}
