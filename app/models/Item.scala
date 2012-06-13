package models

import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data.FormError
import play.api.Play.current
import play.Logger

case class Item(id: Pk[Long] = null, name: String, description: String, item_type_id: Int, vendor_id: Int, cost: BigDecimal, url: String, created_by_id:Int) {
  def save() {
    Item.save(this)
  }

  def update(id: Long) = {
    Item.update(id, this)
  }

  def itemType = ItemType.findById(item_type_id).getOrElse(null)

  def vendor = Vendor.findById(vendor_id).get

  def createdBy = Member.findById(created_by_id)
}

object Item {


  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Int]("item_type_id") ~
    get[Int]("vendor_id") ~
    get[BigDecimal]("cost") ~
    get[String]("url") ~
    get[Int]("created_by_id") map {
      case id~name~description~item_type_id~vendor_id~cost~url~created_by_id => Item(id, name, description, item_type_id, vendor_id, cost, url,created_by_id)
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
      SQL("insert into item (name, description, item_type_id, vendor_id, cost, url, created_by_id) select {name}, {description}, {item_type_id}, {vendor_id}, {cost}, {url}, {created_by_id}").on(
        'name -> item.name,
        'description -> item.description,
	'item_type_id -> item.item_type_id,
	'vendor_id -> item.vendor_id,
        'cost -> item.cost,
         'url -> item.url,
	'created_by_id -> item.created_by_id
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

