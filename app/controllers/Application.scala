package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._


import models.Member
import models.Coop

object Application extends Controller {
  
  def start = Action {
    Ok("Start page")
  }


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action {
    Ok("this is a test....")
  }                     


}
