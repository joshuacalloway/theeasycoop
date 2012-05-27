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

object CoopController extends AbstractCRUDController {
  override type ModelType = Coop

  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "manager_id" -> number
    )(Coop.apply)(Coop.unapply))

  val newMemberForm = Form(
    "name" -> nonEmptyText
  )

  override protected def model_all() = Coop.all
  override protected def model_findById(id: Long) = Coop.findById(id)
  override protected def model_delete(id: Long) = Coop.delete(id)
  override protected def listView = views.html.coop.list(model_all)
  

  def newItem = Action {
    Ok(html.coop.newItem(form))
  }

  // def newMember(id: Long) = Action {
  //   Ok(html.coop.newMember(id, newMemberForm))
  // }

  // def saveMember(id: Long) = Action { implicit request =>
  //   newMemberForm.bindFromRequest.fold(
  //     formWithErrors => BadRequest(views.html.coop.newMember(id, formWithErrors)),
  //     {case (name) => {
  //       val memberOption = Member.findByName(name)
  //       Coop.addMember(id, memberOption.get)
  //       val members = Member.findByCoopId(id)
  //       val coop = Coop.findById(id)
  //       Ok(html.coop.showItem(coop.get, members))
  //     }}
  //    )
  //                                  }


  override def showItem(id: Long) : play.api.mvc.Action[play.api.mvc.AnyContent] =
    Action {
      val itemOption = model_findById(id)
      itemOption match {
      case None => Ok("no item exists")
      case Some(item) => {
        val members = Member.findByCoopId(item.id.get)
        Ok(html.coop.showItem(item, members))
      }
      }
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

  def editItem(id: Long) = Action {
    Coop.findById(id).map { item =>
      Ok(html.coop.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

}
