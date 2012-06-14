package actions

import anorm._
import models.Vendor
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import helpers.Utils

object ShowVendorAction extends Controller {
  
  def showVendor(id: Long) = Action 
  { implicit request =>
    val member = Utils.getLoggedInUser(request.session)
   Vendor.findById(id) match {
     case Some(item) if item.createdBy.get == member => Redirect(actions.routes.EditVendorAction.editVendor(id))
     case Some(item) => Ok(html.showvendor(item))
     case None => Ok("no vendor exists")
   }
 }    
}
