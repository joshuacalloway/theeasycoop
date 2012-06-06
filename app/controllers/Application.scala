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
    val member = request.session.get("username") match {
      case Some(email) => Member.findByEmail(email)
      case _ => None
    }
       
    member match {
      case Some(m) if m.coops.size > 1 => Ok(views.html.index(m, m.coops))
      case Some(m) if m.coops.size == 1 => Ok(views.html.showcoop(m.coops(0), m.coops(0).members))
      case _ => Ok(views.html.index(null,null))
    }
		    }

 

  def test = Action {
    Ok("this is a test....")
  }                     


}
