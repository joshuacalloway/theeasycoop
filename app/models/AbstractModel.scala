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
  //val mapping: Mapping;

  def save
  def update(id: Long)
  // def findById(id: Long): Option[AbstractModel];


  // def save(item: AbstractModel);

  // def update(id: Long, item: AbstractModel);

  // def create(item: AbstractModel);

  // def delete(id: Long);

}

