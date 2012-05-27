package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Coop(id: Pk[Long], name: String, description: Option[String], manager_id: Int) extends AbstractModel {

  def save = {
    Coop.save(this)
  }
  def update(id: Long) = {
    Coop.update(id, this)
  }
  def all = Coop.all

  def manager = Member.findById(manager_id).get

  def isManager(member: Member) : Boolean = {
    member.id == manager.id
  }
}

object Coop {
  
  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[Option[String]]("description") ~
    get[Int]("manager_id") map {
      case id~name~description~manager_id => Coop(id, name, description, manager_id)
    }
  }

  def isManagerOf(id: Long , email: String): Boolean= {
    val coop = Coop.findById(id).get
    val ret = coop.isManager(Member.findByEmail(email).get)
    Logger.info("isManagerOf, coop : " + coop.name +", email : " + email)
    ret
  }
  def findById(id: Long): Option[Coop] = DB.withConnection
  {
    implicit c => SQL("select c.*, m.name as manager from coop c, member m where c.manager_id = m.id and c.id = {id}").on('id -> id).as(Coop.mapping.singleOpt)
  }

  def all(): List[Coop] = DB.withConnection { implicit c =>
    SQL("select c.*, m.name as manager from coop c, member m where c.manager_id = m.id").as(mapping *)
                                           }
  def addMember(id: Long, member: Member) {
    DB.withConnection { implicit c =>
      SQL("insert into coop_member (coop_id, member_id) values ({coop_id}, {member_id})").on(
        'coop_id -> id,
        'member_id -> member.id).executeUpdate()
                     }
  }
  def save(coop: Coop) {
    Logger.info("save entry version 1.0... coop.id: " + coop.id)
    create(coop)
  }

  def update(id: Long, coop: Coop) {
    DB.withConnection { implicit c =>
      SQL("update Coop set name={name}, description={description} where id={id}").on(
        'name -> coop.name,
        'description -> coop.description,
        'id -> id
      ).executeUpdate()
                     }
  }

  def create(coop: Coop) {
    DB.withConnection { implicit c =>
      SQL("insert into Coop (name, description, manager_id) select {name}, {description}, id from member where name = {manager}").on(
        'name -> coop.name,
        'description -> coop.description,
        'manager -> coop.manager
      ).executeUpdate()
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
