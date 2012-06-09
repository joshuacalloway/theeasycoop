package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class ItemType(id: Pk[Long], item_type: String) extends AbstractModel {
  val name = item_type
  def all = ItemType.all

  def save = Nil
  def update(id: Long) = Nil
  
}

object ItemType {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("item_type") map {
      case id~item_type => ItemType(id, item_type)
    }
  }
     
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from item_type order by item_type").as(ItemType.mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[ItemType] = DB.withConnection
  {
    implicit c => SQL("select * from item_type where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[ItemType] = DB.withConnection { implicit c =>
    SQL("select * from item_type").as(mapping *)
                                           }
  def create(item: ItemType) {
    DB.withConnection { implicit c =>
      SQL("insert into item_type (item_type)").on(
        'item_type -> item.item_type
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from item_type where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
