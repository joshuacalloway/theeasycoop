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
    val username = request.session.get("username")
    Logger.info("username is : " + request.session.get("username"))
		     
    Ok(views.html.index())
		    }

 

  def test = Action {
    Ok("this is a test....")
  }                     


}
