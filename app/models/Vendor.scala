package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Vendor(id: Pk[Long], name: String, address:String, zip_code:String,url:String) extends AbstractModel {
  def all = Vendor.all

  def save = {
    Vendor.save(this)
  }

  def update(id: Long) = Nil
  
  def items = Item.findByVendorId(id)
}

object Vendor {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("address") ~
    get[String]("zip_code") ~
    get[String]("url") map {
      case id~name~address~zip_code~url => Vendor(id, name, address, zip_code, url)
    }
  }
     
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from vendor order by name").as(Vendor.mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[Vendor] = DB.withConnection
  {
    implicit c => SQL("select * from vendor where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[Vendor] = DB.withConnection { implicit c =>
    SQL("select * from vendor").as(mapping *)
                                           }

  def save(item: Vendor) {
    create(item)
  }

  def create(item: Vendor) {
    DB.withConnection { implicit c =>
      SQL("insert into vendor (name,address,zip_code,url) select {name},{address},{zip_code},{url} ").on(
        'name -> item.name,
	'address -> item.address,
	'zip_code -> item.zip_code,
	'url -> item.url
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from vendor where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
