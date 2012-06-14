package actions

import anorm._
import models.Coop
import models.CoopForm
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowCoopAction extends Controller {
  
  val form: Form[Coop] = CoopForm.form

  def showItem(id: Long) = Action { implicit request =>
      val itemOption = Coop.findById(id)
      itemOption match {
	case Some(item) => {
          Ok(html.showcoop(item))
	}
	case None => Redirect(controllers.routes.Application.index)
      }
				 }    
}
