package models

import java.util.{Date}
import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data.FormError
import play.api.Play.current
import play.Logger


case class BulkitemOrder(id: Pk[Long] = null, bulkitem_id: Int, minimumbuyers: Int, itemcost: BigDecimal, itemdescription: String, deadline_by: Date, deliveryaddress: String, created_at: Date, created_by_id: Int, var coop_id: Int) extends AbstractModel {
  val name: String = "UNDEFINED"

  def save = {
    BulkitemOrder.save(this)
  }


  def update(id: Long) = {
    BulkitemOrder.update(id, this)
  }
  def all = BulkitemOrder.all

  def bulkitem = {
    Bulkitem.findById(bulkitem_id).get
  }
  def createdBy() = Member.findById(created_by_id).get

  def coop = { Coop.findById(coop_id).get }
  def members = { Member.findByBulkitemOrderId(id.get) }
}

object BulkitemOrder {


  val mapping = {
    get[Pk[Long]]("id") ~
    get[Int]("bulkitem_id") ~
    get[Int]("minimumbuyers") ~
    get[BigDecimal]("itemcost") ~
    get[String]("itemdescription") ~
    get[Date]("deadline_by") ~
    get[String]("deliveryaddress")~
    get[Date]("created_at") ~
    get[Int]("created_by_id") ~
    get[Int]("coop_id") map {
      case id~bulkitem_id~minimumbuyers~itemcost~itemdescription~deadline_by~deliveryaddress~created_at~created_by_id~coop_id => BulkitemOrder(id,bulkitem_id,minimumbuyers,itemcost,itemdescription,deadline_by,deliveryaddress,created_at,created_by_id,coop_id)

    }
  }


  def findByCoopId(id: Long): List[BulkitemOrder] = DB.withConnection
  {
    implicit c => SQL("select * from bulkitemorder where coop_id = {id}").on('id -> id).as(BulkitemOrder.mapping *)
  }

  def findById(id: Long): Option[BulkitemOrder] = DB.withConnection
  {
    implicit c => SQL("select * from bulkitemorder where id = {id}").on('id -> id).as(BulkitemOrder.mapping.singleOpt)
  }

  def all(): List[BulkitemOrder] = DB.withConnection { implicit c =>
    SQL("select * from bulkitemorder").as(mapping *)
                                               }

  def save(item: BulkitemOrder) {
    Logger.info("save entry version 1.0... bulkitemorder.id: " + item.id)
    create(item)
  }

  def update(id: Long, item: BulkitemOrder) {
    DB.withConnection { implicit c =>
      SQL("update bulkitemorder set minimumbuyers={minimumbuyers} where id={id}").on(
        'minimumbuyers -> item.minimumbuyers,
        'id -> id
      ).executeUpdate()
                     }
  }

  def create(item: BulkitemOrder) {
    DB.withConnection { implicit c =>
      SQL("insert into bulkitemorder (bulkitem_id,minimumbuyers,deadline_by,deliveryaddress,created_at,created_by_id,coop_id,itemcost,itemdescription) select {bulkitem_id},{minimumbuyers},{deadline_by},{deliveryaddress},{created_at},{created_by_id},{coop_id},{itemcost},{itemdescription}").on(
        'bulkitem_id -> item.bulkitem_id,
        'minimumbuyers -> item.minimumbuyers,
        'deadline_by -> item.deadline_by,
        'deliveryaddress -> item.deliveryaddress,
        'created_at -> item.created_at,
        'created_by_id -> item.created_by_id,
        'coop_id -> item.coop_id,
        'itemcost -> item.itemcost,
        'itemdescription -> item.itemdescription
      ).executeUpdate()
                     }
  }
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from bulkitemorder where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}

