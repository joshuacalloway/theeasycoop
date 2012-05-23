package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import views._

import models.Member
import models.Coop

object MemberController extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok("listing coops")
  }

  def show(id: Long) = Action {
    val memberOption = Member.findById(id)
    memberOption match {
      case None => Ok("no member exists")
      case Some(member) => {
        Ok(html.member.show(member))
      }
    }    
  }
}
