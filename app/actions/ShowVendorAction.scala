package actions

import anorm._
import models.Vendor
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._

object ShowVendorAction extends Controller {
  
  def showVendor(id: Long) = Action { implicit request =>
      val vendorOption = Vendor.findById(id)
      vendorOption match {
      case None => Ok("no vendor exists")
      case Some(item) => {
        Ok(html.showvendor(item))
      }
      }
    }    
}
