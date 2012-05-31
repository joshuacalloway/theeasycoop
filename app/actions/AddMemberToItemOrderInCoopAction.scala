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

object AddMemberToItemOrderInCoopAction extends Controller with Secured {

  val form = Form(
    "name" -> nonEmptyText
  )

  def actionForm(coopId: Long, id: Long) = Action {
    Ok(html.addMemberToItemOrderInCoop(coopId, id, form))
  }

  def submitForm(coopId: Long, id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.addMemberToCoop(id, formWithErrors)),
      {case (value) => {
        val memberOption = Member.findById(value.toInt)
        ItemOrder.addMember(id, memberOption.get)
        Ok(html.listMembersInItemOrderInCoop(ItemOrder.findById(id).get.members))
      }}
     )
                                   }
}
