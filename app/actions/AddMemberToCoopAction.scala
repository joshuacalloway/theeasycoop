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
        val memberOption = Member.findById(value.toInt)
        Coop.addMember(id, memberOption.get)
        val members = Member.findByCoopId(id)
        val coop = Coop.findById(id)
        Ok(html.coop.showItem(coop.get, members))
      }}
     )
                                   }
}
