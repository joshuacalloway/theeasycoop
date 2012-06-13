package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger
import play.api.data.Forms._
import play.api.mvc._
import views._
import play.api.data._

case class Coop(id: Pk[Long], name: String, description: String, coop_type_id: Int, manager_id: Int, state_id: Int, zip_code: String) extends AbstractModel {

  def save = {
    Coop.save(this)
  }
  def update(id: Long) = {
    Coop.update(id, this)
  }
  def all = Coop.all

  def manager = Member.findById(manager_id).get

  def coopType = CoopType.findById(coop_type_id).get

  def state = State.findById(state_id).get

  def isManager(member: Member) : Boolean = {
    (member, manager) match {
      case (x: Member, y:Member) => x.id == y.id
      case _ => false
    }
  }

  def isMember(member: Member) : Boolean = {
    Coop.isMember(this, member)
  }
  def members = Member.findByCoopId(id)
  def itemOrders = ItemOrder.findByCoopId(id)
}

object Coop {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Int]("coop_type_id") ~
    get[Int]("manager_id") ~
    get[Int]("state_id") ~
    get[String]("zip_code") map {
      case id~name~description~coop_type_id~manager_id~state~zip_code => Coop(id, name, description, coop_type_id, manager_id, state, zip_code)
    }
  }

  def isManagerOf(id: Long , email: String): Boolean= {
    val coop = Coop.findById(id)
    val member = Member.findByEmail(email)
    (coop, member) match {
      case (Some(c), Some(m)) => return c.isManager(m)
      case _ => return false }
  }
  def findById(id: Long): Option[Coop] = DB.withConnection
  {
    implicit c => SQL("select * from coop where id = {id}").on('id -> id).as(Coop.mapping.singleOpt)
  }

  def all(): List[Coop] = DB.withConnection { implicit c =>
    SQL("select * from coop").as(mapping *)
                                           }
  def addMember(id: Long, member: Member) {
    DB.withConnection { implicit c =>
      SQL("insert into coop_member (coop_id, member_id) values ({coop_id}, {member_id})").on(
        'coop_id -> id,
        'member_id -> member.id).executeUpdate()
                     }
  }

  def findByMemberId(memberId: Pk[Long]): List[Coop] = DB.withConnection
  {
    implicit c => SQL("select c.* from coop c, coop_member cm where cm.coop_id = c.id and cm.member_id = {memberId}").on('memberId -> memberId).as(mapping *)
  }

  def save(coop: Coop) {
    Logger.info("save entry version 1.0... coop.id: " + coop.id)
    create(coop)
  }

  def update(id: Long, coop: Coop) {
    DB.withConnection { implicit c =>
      SQL("update Coop set name={name}, description={description}, coop_type_id={coop_type_id} where id={id}").on(
        'name -> coop.name,
        'description -> coop.description,
	'coop_type_id -> coop.coop_type_id,
        'id -> id
      ).executeUpdate()
                     }
  }

  def isMember(coop: Coop, member: Member) : Boolean = {
    DB.withConnection { implicit c =>
      val ret:Long = SQL("select count(*) from coop c, coop_member cm where cm.coop_id = c.id and c.id = {coop_id} and cm.member_id = {member_id}").on('coop_id -> coop.id, 'member_id -> member.id).as(scalar[Long].single)
                       ret == 1
                     }
  }
      // val id:Long = SQL("select currval('coop_id_seq')").as(anorm.ResultSetParser[Long])

  def create(coop: Coop) {
    DB.withConnection { implicit c =>
      SQL("insert into Coop (name, description, manager_id,coop_type_id,state_id,zip_code) select {name}, {description}, {manager_id},{coop_type_id},{state_id},{zip_code} ").on(
        'name -> coop.name,
        'description -> coop.description,
        'manager_id -> coop.manager_id,
        'coop_type_id -> coop.coop_type_id,
	'state_id -> coop.state_id,
	'zip_code -> coop.zip_code
      ).executeInsert() match {
        case Some(id) =>
          SQL("insert into coop_member (coop_id,member_type_id,member_status_id, member_id) values ({coop_id},{member_type_id},{member_status_id}, {member_id})").on(
            'coop_id -> id,
            'member_type_id -> MemberType.MANAGER_ID,
            'member_status_id -> 1,
            'member_id -> coop.manager_id).executeUpdate()
        case None => Logger.warn("Could not auto add manager when creating a new coop.")
        }
                       
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from coop where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
