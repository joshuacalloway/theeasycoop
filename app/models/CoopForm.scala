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

object CoopForm {

  val form: Form[Coop] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "name" -> text,
      "description" -> nonEmptyText,
      "coop_type_id" -> number,
      "manager_id" -> number,
      "state_id" -> number,
      "zip_code" -> nonEmptyText
    )(Coop.apply)(Coop.unapply))

}
