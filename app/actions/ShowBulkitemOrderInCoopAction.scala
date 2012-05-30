package actions

import anorm._
import models.Coop
import models.BulkitemOrder
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowBulkitemOrderInCoopAction extends Controller with Secured {

  def showItem(coopId: Long, id: Long) : play.api.mvc.Action[play.api.mvc.AnyContent] =
    Action {
      val coopOpt = Coop.findById(coopId)
      val itemOpt = BulkitemOrder.findById(id)
      (coopOpt, itemOpt) match {
        case (Some(coop), Some(item)) if coop.id == item.coop.get.id => Ok("bulkitem exists")
        case _ => Ok("bulkitem does not belong in coop")
      }
    }    

}
