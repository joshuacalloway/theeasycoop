package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Member(id: Long, name: String, email: Option[String])

object Member {
  
  val member = {
    get[Long]("id") ~
    get[String]("name") ~
    get[Option[String]]("email") map {
      case id~name~email => Member(id, name, email)
    }
  }

  def findByName(name: String): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where name = {name}").on('name -> name).as(member.singleOpt)
  }

  def findById(id: Long): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where id = {id}").on('id -> id).as(member.singleOpt)
  }

  def findByCoopId(coopId: Long): List[Member] = DB.withConnection
  {
    implicit c => SQL("select m.* from member m, coop_member cm where cm.member_id = m.id and cm.coop_id = {coopId}").on('coopId -> coopId).as(member *)
  }

  def all(): List[Member] = DB.withConnection { implicit c =>
    SQL("select * from member").as(member *)
                                           }

  def create(name: String) {
    DB.withConnection { implicit c =>
      SQL("insert into member (name) values ({name})").on(
        'name -> name
      ).executeUpdate()
                     }
  }
  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from member where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
