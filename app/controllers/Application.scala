package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._


import models.Member
import models.Coop

object Application extends Controller {

  val memberForm = Form(
    "name" -> nonEmptyText
  )

  val coopForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "manager" -> nonEmptyText
    ))


  
  def start = Action {
    Ok(views.html.start(coopForm))
  }

  def members = Action {
    Ok(views.html.members(Member.all(), memberForm))
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action {
    Ok("this is a test....")
  }

  def newCoop = Action { implicit request =>
    coopForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.start(formWithErrors)),
      {case (name, manager) => {
        Coop.create(name, manager)
        Redirect(routes.Application.start())
      }}
     )
                      }  

  def newMember = Action { implicit request =>
    memberForm.bindFromRequest.fold(
      errors => BadRequest(views.html.members(Member.all(), errors)),
      name => {
        Member.create(name)
        Redirect(routes.Application.members)
      }
    )
                      }
  def deleteMember(id: Long) = Action {
    Member.delete(id)
    Redirect(routes.Application.members)
  }
}
