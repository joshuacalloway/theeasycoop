package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data.FormError
import play.api.Play.current
import play.Logger

case class Item(id: Pk[Long] = null, name: String, description: String, cost: BigDecimal, url: String) {
  def save() {
    Item.save(this)
  }
}

object Item {


  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[BigDecimal]("cost") ~
    get[String]("url") map {
      case id~name~cost~description~url => Item(id, name, cost,description, url)
    }
  }

  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from item order by name").as(mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[Item] = DB.withConnection
  {
    implicit c => SQL("select * from item where id = {id}").on('id -> id).as(Item.mapping.singleOpt)
  }

  def all(): List[Item] = DB.withConnection { implicit c =>
    SQL("select * from item").as(mapping *)
                                               }

  def save(item: Item) {
    Logger.info("save entry version 1.0... item.id: " + item.id)
    create(item)
  }

  def update(id: Long, item: Item) {
    DB.withConnection { implicit c =>
      SQL("update item set name={name}, description={description}, cost={cost}, url={url} where id={id}").on(
        'name -> item.name,
        'description -> item.description,
        'url -> item.url,
        'cost -> item.cost,
        'id -> id
      ).executeUpdate()
                     }
  }

  def create(item: Item) {
    DB.withConnection { implicit c =>
      SQL("insert into item (name, description, cost, url) select {name}, {description}, {cost}, {url}").on(
        'name -> item.name,
        'description -> item.description,
        'cost -> item.cost,
         'url -> item.url
      ).executeUpdate()
                     }
  }
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from item where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}

