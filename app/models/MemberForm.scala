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

object MemberForm {

  val form: Form[Member] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> optional(text),
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "cell" -> optional(text),
      "address" -> optional(text),
    "state_id" -> optional(number),
    "zip_code" -> text)(Member.apply)(Member.unapply))
}
