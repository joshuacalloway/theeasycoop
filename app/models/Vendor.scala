package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Vendor(id: Pk[Long], name: String, address:String, state_id:Int, zip_code:String,url:String, created_by_id:Int) extends AbstractModel {
  def all = Vendor.all

  def save = {
    Vendor.save(this)
  }

  def update(id: Long) = {
    Vendor.update(id, this)
  }
  
  def items = Item.findByVendorId(id)

  def createdBy = Member.findById(created_by_id)

}

object Vendor {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("address") ~
    get[Int]("state_id") ~
    get[String]("zip_code") ~
    get[String]("url") ~
    get[Int]("created_by_id") map {
      case id~name~address~state_id~zip_code~url~created_by_id => Vendor(id, name, address, state_id, zip_code, url,created_by_id)
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
      SQL("insert into vendor (name,address,state_id, zip_code,url,created_by_id) select {name},{address},{state_id}, {zip_code},{url},{created_by_id} ").on(
        'name -> item.name,
	'address -> item.address,
	'state_id -> item.state_id,
	'zip_code -> item.zip_code,
	'url -> item.url,
	'created_by_id -> item.created_by_id
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


  def update(id: Long, item: Vendor) {
    DB.withConnection { implicit c =>
      SQL("update vendor set name={name}, address={address}, zip_code={zip_code}, url={url},state_id={state_id} where id={id}").on(
        'name -> item.name,
        'address -> item.address,
        'url -> item.url,
        'zip_code -> item.zip_code,
	'state_id -> item.state_id,
        'id -> id
      ).executeUpdate()
                     }
  }

}
