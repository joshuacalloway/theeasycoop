package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import views._

import models.Member
import models.Coop

object CoopController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok("listing coops")
  }

  val newMemberForm = Form(
    "name" -> nonEmptyText
  )

  def newMember(id: Long) = Action {
    Ok(html.coop.newMember(id, newMemberForm))
  }

  def saveMember(id: Long) = Action { implicit request =>
    newMemberForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.coop.newMember(id, formWithErrors)),
      {case (name) => {
        val memberOption = Member.findByName(name)
        Coop.addMember(id, memberOption.get)
        val members = Member.findByCoopId(id)
        val coop = Coop.findById(id)
        Ok(html.coop.show(coop.get, members))
      }}
     )
                                   }

  def members(id: Long) = Action {
    val coopOption = Coop.findById(id)
    coopOption match {
      case None => Ok("no coop exists")
      case Some(coop) => {
        val members = Member.findByCoopId(coop.id)
        Ok(html.coop.show(coop, members))
      }
    }    
  }

  def show(id: Long) = Action {
    val coopOption = Coop.findById(id)
    coopOption match {
      case None => Ok("no coop exists")
      case Some(coop) => {
        val members = Member.findByCoopId(coop.id)
        Ok(html.coop.show(coop, members))
      }
    }    
  }
}
