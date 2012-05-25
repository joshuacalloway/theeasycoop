package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Coop(id: Long, name: String, description: Option[String], manager: String)

object Coop {
  
  val coop = {
    get[Long]("id") ~
    get[String]("name") ~
    get[Option[String]]("description") ~
    get[String]("manager") map {
      case id~name~description~manager => Coop(id, name, description, manager)
    }
  }

  def findById(id: Long): Option[Coop] = DB.withConnection
  {
    implicit c => SQL("select c.*, m.name as manager from coop c, member m where c.manager_id = m.id and c.id = {id}").on('id -> id).as(Coop.coop.singleOpt)
  }

  def all(): List[Coop] = DB.withConnection { implicit c =>
    SQL("select * from coop").as(coop *)
                                           }
  def addMember(id: Long, member: Member) {
    DB.withConnection { implicit c =>
      SQL("insert into coop_member (coop_id, member_id) values ({coop_id}, {member_id})").on(
        'coop_id -> id,
        'member_id -> member.id).executeUpdate()
                     }
  }

  def create(name: String, description: String, manager : String) {
    DB.withConnection { implicit c =>
      SQL("insert into Coop (name, description, manager_id) select {name}, {description}, id from member where name = {manager}").on(
        'name -> name,
        'description -> description,
        'manager -> manager
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
