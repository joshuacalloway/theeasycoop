package models

import anorm._
import play.api.Play.current
import play.api._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views._
import actions.Secured
import play.api.data.validation.Constraints._
import helpers.CustomFormats._

object VendorForm {

  val form: Form[Vendor] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "state_id" -> number,
      "zip_code" -> nonEmptyText,
      "url" -> text,
    "created_by_id" -> number)
    (Vendor.apply)(Vendor.unapply))
}
