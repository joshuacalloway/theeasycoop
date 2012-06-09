package actions

import anorm._
import models.Item
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowItemAction extends Controller {
  
  def showItem(id: Long) = Action { implicit request =>
      val itemOption = Item.findById(id)
      itemOption match {
      case None => Ok("no item exists")
      case Some(item) => {
        Ok(html.showitem(item))
      }
      }
    }    
}
