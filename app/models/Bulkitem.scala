package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data.FormError
import play.api.Play.current
import play.Logger

case class Bulkitem(id: Pk[Long] = null, name: String, description: String, cost: BigDecimal, url: String) {
  def save() {
    Bulkitem.save(this)
  }
}

object Bulkitem {


  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[BigDecimal]("cost") ~
    get[String]("url") map {
      case id~name~cost~description~url => Bulkitem(id, name, cost,description, url)
    }
  }

  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from bulkitem order by name").as(mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[Bulkitem] = DB.withConnection
  {
    implicit c => SQL("select * from bulkitem where id = {id}").on('id -> id).as(Bulkitem.mapping.singleOpt)
  }

  def all(): List[Bulkitem] = DB.withConnection { implicit c =>
    SQL("select * from bulkitem").as(mapping *)
                                               }

  def save(bulkitem: Bulkitem) {
    Logger.info("save entry version 1.0... bulkitem.id: " + bulkitem.id)
    create(bulkitem)
  }

  def update(id: Long, bulkitem: Bulkitem) {
    DB.withConnection { implicit c =>
      SQL("update bulkitem set name={name}, description={description}, cost={cost}, url={url} where id={id}").on(
        'name -> bulkitem.name,
        'description -> bulkitem.description,
        'url -> bulkitem.url,
        'cost -> bulkitem.cost,
        'id -> id
      ).executeUpdate()
                     }
  }

  def create(bulkitem: Bulkitem) {
    DB.withConnection { implicit c =>
      SQL("insert into bulkitem (name, description, cost, url) select {name}, {description}, {cost}, {url}").on(
        'name -> bulkitem.name,
        'description -> bulkitem.description,
        'cost -> bulkitem.cost,
         'url -> bulkitem.url
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

