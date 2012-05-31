package controllers

import anorm._
import models.Coop
import models.Member
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import actions.Secured

object CoopController extends AbstractCRUDController with Secured {
  override type ModelType = Coop

  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> optional(nonEmptyText),
      "coop_type_id" -> number,
      "manager_id" -> number
    )(Coop.apply)(Coop.unapply))

  override protected def model_all() = Coop.all
  override protected def model_findById(id: Long) = Coop.findById(id)
  override protected def model_delete(id: Long) = Coop.delete(id)
  override protected def listView = views.html.admin.coop.list(model_all)
  

  def newItem = Action {
      Ok(html.admin.coop.newItem(form)) 
  }

  override def showItem(id: Long) : play.api.mvc.Action[play.api.mvc.AnyContent] =
    Action {
      val itemOption = model_findById(id)
      itemOption match {
      case None => Ok("no item exists")
      case Some(item) => {
        val members = Member.findByCoopId(item.id.get)
        Ok(html.admin.coop.showItem(item, members))
      }
      }
    }    

  def editItem(id: Long) = Action {
    Coop.findById(id).map { item =>
      Ok(html.admin.coop.editItem(id, form.fill(item)))
                         }.getOrElse(NotFound)
  }

}
