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

  def actionForm(id: Long) = IsManagerOf(id) { _ => _ =>
    Ok(html.public.actions.addMemberToCoop(id, form))
  }

  def submitForm(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.public.actions.addMemberToCoop(id, formWithErrors)),
      {case (value) => {
        val memberOpt = Member.findById(value.toInt)
        val coopOpt = Coop.findById(id)
        (coopOpt, memberOpt) match {
          case (Some(coop), Some(member)) => {
            val members = Member.findByCoopId(id)
            if (!coop.isMember(member))
              Coop.addMember(id, memberOpt.get)
            Ok(html.coop.showItem(coop, members))
          }
          case _ => Ok("Could not add member to coop")
        }
      }}
     )
                                   }
}
