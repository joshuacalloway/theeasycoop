package controllers

import anorm._
import models.Member
import models.MemberForm
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import actions.Secured

object MemberController extends AbstractCRUDController with Secured {
  override type ModelType = Member
  override type FormType = AbstractForm[Member]
  val form: Form[Member] = MemberForm.form

  override protected def model_all() = {
    Logger.info("model_all called")
    Member.all
  }

  override protected def model_findById(id: Long) = Member.findById(id)
  override protected def model_delete(id: Long) = Member.delete(id)
  override protected def listView = views.html.admin.member.list(model_all)

  def newItem = Action {
    Ok(html.admin.member.newItem(form)) 
  }

  def editItem(id: Long) = Action {
    Member.findById(id) match { 
      case Some(item) =>
        Ok(html.admin.member.editItem(id, form.fill(item)))
      case _ => Ok("error")
    }
  }
}
