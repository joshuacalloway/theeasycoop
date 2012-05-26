package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import views._

import models.Member
import models.Coop

object MemberController extends Controller {

  val form = Form(
    "name" -> nonEmptyText
  )

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action {
    Ok(html.member.list(Member.all(), form))
  }


  def showItem(id: Long) = Action {
    val memberOption = Member.findById(id)
    memberOption match {
      case None => Ok("no member exists")
      case Some(member) => {
        Ok(html.member.showItem(member))
      }
    }    
  }

  def newItem = Action { implicit request =>
    form.bindFromRequest.fold(
      errors => BadRequest(html.member.list(Member.all(), errors)),
      name => {
        Member.create(name)
        Redirect(routes.CoopController.list)
      }
    )
                      }
  def deleteItem(id: Long) = Action {
    Member.delete(id)
    Redirect(routes.CoopController.list)
  }
}
