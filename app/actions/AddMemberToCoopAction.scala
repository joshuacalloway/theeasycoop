package actions

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
import anorm.SqlParser._
import views._

import models.Coop
import models.Member
import helpers.CustomFormats._



object AddMemberToCoopAction extends Controller {

  val form = Form(
    "name" -> nonEmptyText
  )

  def actionForm(id: Long) = Action {
    Ok(html.coop.newMember(id, form))
  }

  def submitForm(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.coop.newMember(id, formWithErrors)),
      {case (name) => {
        val memberOption = Member.findByName(name)
        Coop.addMember(id, memberOption.get)
        val members = Member.findByCoopId(id)
        val coop = Coop.findById(id)
        Ok(html.coop.showItem(coop.get, members))
      }}
     )
                                   }
}
