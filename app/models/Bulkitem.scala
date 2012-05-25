package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Bulkitem(id: Pk[Long], name: String, description: String, url: String)

object Bulkitem {
  
  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
//    get[BigDecimal]("cost") ~
    get[String]("url") map {
      case id~name~description~url => Bulkitem(id, name, description, url)
    }
  }

  def findById(id: Long): Option[Bulkitem] = DB.withConnection
  {
    implicit c => SQL("select * from bulkitem where id = {id}").on('id -> id).as(Bulkitem.mapping.singleOpt)
  }

  def all(): List[Bulkitem] = DB.withConnection { implicit c =>
    SQL("select * from bulkitem").as(mapping *)
                                               }

  def save(bulkitem: Bulkitem) {
    //if (bulkitem.id == -1) {
      create(bulkitem.name, bulkitem.description, bulkitem.url)
    //}
  }

  def create(name: String, description: String, url: String) {
    DB.withConnection { implicit c =>
      SQL("insert into bulkitem (name, description, cost, url) select {name}, {description}, 0.99, {url}").on(
        'name -> name,
        'description -> description,
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

