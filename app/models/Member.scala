package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Member(id: Pk[Long], name: String, email: Option[String]) extends AbstractModel {
  
  def save = {
    Member.save(this)
  }
  def update(id: Long) = {
    Member.update(id, this)
  }
  def all = Member.all
}

object Member {
  
  val member = {
    get[Pk[Long]]("id") ~
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

  def save(item: Member) {
    Logger.info("save entry version 1.0... item.id: " + item.id)
    create(item)
  }

  def create(item: Member) {
    DB.withConnection { implicit c =>
      SQL("insert into member (name, email) values ({name}, {email})").on(
        'name -> item.name,
        'email -> item.email
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

  def update(id: Long, item: Member) {
    Logger.info("updating member : " + id + " with email : " + item.email)
    DB.withConnection { implicit c =>
      SQL("update member set name={name}, email={email} where id={id}").on(
        'name -> item.name,
        'email -> item.email,
        'id -> id
      ).executeUpdate()
                     }
  }


}
