package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._


import models.Member
import models.Coop
import actions.Secured

object Application extends Controller with Secured {
  
  def start = Action {
    Ok("Start page")
  }



  def index = Action { implicit request =>
    request.session.get("username") match {
      case Some(email) => {
	val member = Member.findByEmail(email)
	if (member.get.coops.size > 0) Ok(views.html.showcoop(member.get.coops(0)))
	else Ok(views.html.index(member.get))
      }
      case _ => Ok("User not logged in")
    }
		    }

 

  def test = Action {
    Ok("this is a test....")
  }                     


}
