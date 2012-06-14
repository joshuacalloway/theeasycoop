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

object ItemOrderForm {

  val form: Form[ItemOrder] = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "bulkitem_id" -> number,
      "minimumbuyers" -> number,
      "itemcost" -> money,
      "itemdescription" -> text,
      "paymentinstructions" -> text,
      "deadline_by" -> date("yyyy-MM-dd"),
      "deliveryaddress" -> text,
      "created_at" -> date("yyyy-MM-dd"),
      "created_by_id" -> number,
      "coop_id" -> number
    )(ItemOrder.apply)(ItemOrder.unapply))
}
