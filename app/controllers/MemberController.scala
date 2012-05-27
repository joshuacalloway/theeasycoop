package controllers

import anorm._
import models.Member
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object MemberController extends AbstractCRUDController {
  override type ModelType = Member
  override type FormType = AbstractForm[Member]
  val form: Form[Member] = AbstractForm(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "email" -> optional(text),
      "password" -> nonEmptyText,
      "member_status_id" -> number,
      "member_type_id" -> number)(Member.apply)(Member.unapply))

  override protected def model_all() = {
    Logger.info("model_all called")
    Member.all
  }
  override protected def model_findById(id: Long) = Member.findById(id)
  override protected def model_delete(id: Long) = Member.delete(id)
  override protected def listView = views.html.member.list(model_all)

  def newItem = Action {
    Ok(html.member.newItem(form))
  }

  def editItem(id: Long) = Action {
    Member.findById(id).map { item =>
      Ok(html.member.editItem(id, form.fill(item)))
                           }.getOrElse(NotFound)
  }
}
