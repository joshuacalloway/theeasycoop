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

object ItemForm {

  val form: Form[Item] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "item_type_id" -> number,
      "vendor_id" -> number,
      "cost" -> money,
      "url" -> text,
    "created_by_id" -> number)
    (Item.apply)(Item.unapply))
}
