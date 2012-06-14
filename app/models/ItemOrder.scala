package models

import java.util.{Date}
import java.math.BigDecimal
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.data.FormError
import play.api.Play.current
import play.Logger


case class ItemOrder(id: Pk[Long] = null, item_id: Int, minimumbuyers: Int, membercost: BigDecimal, description: String, deadline_by: Date, deliveryaddress: String, created_at: Date, created_by_id: Int, var coop_id: Int) extends AbstractModel {
  val name: String = "UNDEFINED"

  def save = {
    ItemOrder.save(this)
  }


  def update(id: Long) = {
    ItemOrder.update(id, this)
  }
  def all = ItemOrder.all

  def item = {
    Item.findById(item_id).get
  }
  def createdBy = Member.findById(created_by_id).get

  def coop = { Coop.findById(coop_id).get }
  def members = { Member.findByItemOrderId(id.get) }
  def isMemberIn(m: Member) : Boolean = {
    members.foreach{ i =>
      if (i == m) return true
		  }
    return false
  }
}

object ItemOrder {


  val mapping = {
    get[Pk[Long]]("id") ~
    get[Int]("item_id") ~
    get[Int]("minimumbuyers") ~
    get[BigDecimal]("membercost") ~
    get[String]("description") ~
    get[Date]("deadline_by") ~
    get[String]("deliveryaddress")~
    get[Date]("created_at") ~
    get[Int]("created_by_id") ~
    get[Int]("coop_id") map {
      case id~item_id~minimumbuyers~membercost~description~deadline_by~deliveryaddress~created_at~created_by_id~coop_id => ItemOrder(id,item_id,minimumbuyers,membercost,description,deadline_by,deliveryaddress,created_at,created_by_id,coop_id)

    }
  }

  def findByCoopId(id: Long): List[ItemOrder] = DB.withConnection
  {
    implicit c => SQL("select * from itemorder where coop_id = {id}").on('id -> id).as(ItemOrder.mapping *)
  }


  def findByCoopId(id: Pk[Long]): List[ItemOrder] = 
  {
    findByCoopId(id.get)
  }

  def findById(id: Long): Option[ItemOrder] = DB.withConnection
  {
    implicit c => SQL("select * from itemorder where id = {id}").on('id -> id).as(ItemOrder.mapping.singleOpt)
  }

  def all(): List[ItemOrder] = DB.withConnection { implicit c =>
    SQL("select * from itemorder").as(mapping *)
                                               }

  def save(item: ItemOrder) {
    Logger.info("save entry version 1.0... itemorder.id: " + item.id)
    create(item)
  }

  def update(id: Long, item: ItemOrder) {
    DB.withConnection { implicit c =>
      SQL("update itemorder set minimumbuyers={minimumbuyers} where id={id}").on(
        'minimumbuyers -> item.minimumbuyers,
        'id -> id
      ).executeUpdate()
                     }
  }

  def create(item: ItemOrder) {
    DB.withConnection { implicit c =>
      SQL("insert into itemorder (item_id,minimumbuyers,deadline_by,deliveryaddress,created_at,created_by_id,coop_id,membercost,description) select {item_id},{minimumbuyers},{deadline_by},{deliveryaddress},{created_at},{created_by_id},{coop_id},{membercost},{description}").on(
        'item_id -> item.item_id,
        'minimumbuyers -> item.minimumbuyers,
        'deadline_by -> item.deadline_by,
        'deliveryaddress -> item.deliveryaddress,
        'created_at -> item.created_at,
        'created_by_id -> item.created_by_id,
        'coop_id -> item.coop_id,
        'membercost -> item.membercost,
        'description -> item.description
      ).executeUpdate()
                     }
  }
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from itemorder where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

  def addMember(id: Long, member: Member) {
    DB.withConnection { implicit c =>
      SQL("insert into itemorder_member (member_id, itemorder_id) values ({member_id}, {itemorder_id})").on(
        'itemorder_id -> id,
        'member_id -> member.id).executeUpdate()
                     }
  }

  def removeMember(id: Long, member: Member) {
    DB.withConnection { implicit c =>
      SQL("delete from itemorder_member where member_id={member_id} and itemorder_id = {itemorder_id}").on(
        'itemorder_id -> id,
        'member_id -> member.id).executeUpdate()
                     }
  }

}

