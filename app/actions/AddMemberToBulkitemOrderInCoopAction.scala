package actions

import models.Coop
import models.Member
import models.BulkitemOrder
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object AddMemberToBulkitemOrderInCoopAction extends Controller with Secured {

  val form = Form(
    "name" -> nonEmptyText
  )

  def actionForm(coopId: Long, id: Long) = Action {
    Ok(html.public.actions.addMemberToBulkitemOrderInCoop(coopId, id, form))
  }

  def submitForm(coopId: Long, id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.public.actions.addMemberToCoop(id, formWithErrors)),
      {case (value) => {
        val memberOption = Member.findById(value.toInt)
        BulkitemOrder.addMember(id, memberOption.get)
        Ok(html.public.actions.listMembersInBulkitemOrderInCoop(BulkitemOrder.findById(id).get.members))
      }}
     )
                                   }
}
