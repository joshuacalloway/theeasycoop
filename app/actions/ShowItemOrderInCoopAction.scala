package actions

import anorm._
import models.Coop
import models.ItemOrder
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowItemOrderInCoopAction extends Controller with Secured {

  def showItem(coopId: Long, id: Long) = Action { implicit request =>
      val coopOpt = Coop.findById(coopId)
      val itemOpt = ItemOrder.findById(id)
      (coopOpt, itemOpt) match {
        case (Some(coop), Some(item)) if coop.id == item.coop.id => Ok(html.showitemorderincoop(item))

        case _ => Ok("bulkitem does not belong in coop")
      }
    }    

}
