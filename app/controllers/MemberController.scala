package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import anorm._
import anorm.SqlParser._

import views._

import models.Member

object MemberController extends AbstractCRUDController {
  override type ModelType = Member
  override type FormType = Form[Member]
  val form: Form[Member] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "email" -> optional(text))(Member.apply)(Member.unapply))

  protected def model_all() = Member.all
  protected def model_findById(id: Long) = Member.findById(id)
  protected def model_delete(id: Long) = Member.delete(id)

  override protected val listView = views.html.member.list(model_all)

  def newItem = Action {
    Ok(html.member.newItem(form))
  }

  // def form_fill(item: models.Member) : play.api.data.Form[Member] =
  //   {
  //     form.fill(item)
  //   }

  def editItem(id: Long) = Action {
    Member.findById(id).map { item =>
      Ok(html.member.editItem(id, form.fill(item)))
                           }.getOrElse(NotFound)
  }
}
