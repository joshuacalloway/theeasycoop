package actions

import models.Coop
import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object AddMemberToCoopAction extends Controller with Secured {

  val form = Form(
    "name" -> nonEmptyText
  )

  def actionForm(id: Long) = Action { implicit request =>
    Ok(html.addmembertocoop(id, form))
  }

  def submitForm(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.addmembertocoop(id, formWithErrors)),
      {case (value) => {
        val memberOpt = Member.findById(value.toInt)
        val coopOpt = Coop.findById(id)
        (coopOpt, memberOpt) match {
          case (Some(coop), Some(member)) => {
            if (!coop.isMember(member))
              Coop.addMember(id, memberOpt.get)
            Ok(html.showcoop(coop))
          }
          case _ => Ok("Could not add member to coop")
        }
      }}
     )

                                   }
}
