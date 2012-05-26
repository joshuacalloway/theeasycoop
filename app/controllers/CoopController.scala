package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import views._
import anorm._
import anorm.SqlParser._
import models.Member
import models.Coop


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
import helpers.CustomFormats._

object CoopController extends Controller {
  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "manager" -> nonEmptyText
    )(Coop.apply)(Coop.unapply))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok("listing coops")
  }

  val newMemberForm = Form(
    "name" -> nonEmptyText
  )

  def newItem = Action {
    Ok(html.coop.newItem(form))
  }

  def updateItem(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.coop.editItem(id, formWithErrors)),
      coop => {
        Coop.update(id, coop)
        Ok("saved...")
      }
    )
  }

  def saveItem = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.coop.newItem(formWithErrors)),
      coop => {
        Coop.save(coop)
        Redirect(routes.Application.start())
      })}

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
        Ok(html.coop.showItem(coop.get, members))
      }}
     )
                                   }

  def members(id: Long) = Action {
    val coopOption = Coop.findById(id)
    coopOption match {
      case None => Ok("no coop exists")
      case Some(coop) => {
        val members = Member.findByCoopId(coop.id.get)
        Ok(html.coop.showItem(coop, members))
      }
    }    
  }

  def showItem(id: Long) = Action {
    val coopOption = Coop.findById(id)
    coopOption match {
      case None => Ok("no coop exists")
      case Some(coop) => {
        val members = Member.findByCoopId(coop.id.get)
        Ok(html.coop.showItem(coop, members))
      }
    }    
  }

  def editItem(id: Long) = Action {
    Coop.findById(id).map { coop =>
      Ok(html.coop.editItem(id, form.fill(coop)))
                         }.getOrElse(NotFound)
  }

}
