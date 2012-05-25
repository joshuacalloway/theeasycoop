package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Bulkitem(id: Long, name: String, description: Option[String], cost: BigDecimal, url: Option[String])

object Bulkitem {
  
  val mapping = {
    get[Long]("id") ~
    get[String]("name") ~
    get[Option[String]]("description") ~
    get[BigDecimal]("cost") ~
    get[Option[String]]("url") map {
      case id~name~description~cost~url => Bulkitem(id, name, description, cost, url)
    }
  }

  def findById(id: Long): Option[Bulkitem] = DB.withConnection
  {
    implicit c => SQL("select * from bulkitem where id = {id}").on('id -> id).as(Bulkitem.mapping.singleOpt)
  }

  def all(): List[Bulkitem] = DB.withConnection { implicit c =>
    SQL("select * from bulkitem").as(mapping *)
                                               }

  def create(name: String, description: Option[String], cost: BigDecimal, url: Option[String]) {
    DB.withConnection { implicit c =>
      SQL("insert into bulkitem (name, description, cost, url) select {name}, {description}, {cost}, {url}").on(
        'name -> name,
        'description -> description,
        'cost -> cost,
        'url -> url
      ).executeUpdate()
                     }
  }
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from bulkitem where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
