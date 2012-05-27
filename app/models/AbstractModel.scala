package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data._
import play.api.data.FormError
import play.api.Play.current
import play.Logger

abstract class AbstractModel {
  val name: String
  def save
  def update(id: Long)
  def all : List[AbstractModel]
}

