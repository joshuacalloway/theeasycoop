package actions

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
import views._

import models.Vendor
import helpers.CustomFormats._
import models.VendorForm

object AddVendorAction extends Controller with Secured {
  val form: Form[Vendor] = VendorForm.form

  def saveVendor = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.addvendor(formWithErrors)),
      item => {
        Vendor.save(item)
        Redirect(controllers.routes.Application.index)
      }
      )}

  def newVendor = Action { implicit request =>
    Ok(html.addvendor(form))
  }
}
