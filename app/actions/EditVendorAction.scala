package actions

import anorm._
import models.Vendor
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import helpers.CustomFormats._
import models.VendorForm

object EditVendorAction extends Controller with Secured {
  val form: Form[Vendor] = VendorForm.form

  def editVendor(id: Long) = Action { implicit request => 
    Vendor.findById(id) match { 
      case Some(item) =>
        Ok(html.editvendor(id, form.fill(item)))
      case _ => Ok("error")
    }
  }


  def updateVendor(id: Long) = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.editvendor(id, formWithErrors)),
      item => {
        item.update(id)
	Redirect(controllers.routes.Application.index)
      }
    )
				   }

}
