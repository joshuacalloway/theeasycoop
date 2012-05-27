package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Member(id: Pk[Long], name: String, email: Option[String], password: String, memberStatusId: Int, memberTypeId: Int) extends AbstractModel {
  
  def save = {
    Member.save(this)
  }
  def update(id: Long) = {
    Member.update(id, this)
  }
  def all = Member.all

  
}

object Member {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[Option[String]]("email") ~
    get[String]("password") ~
    get[Int]("member_status_id") ~
    get[Int]("member_type_id") map {
      case id~name~email~password~member_status_id~member_type_id => Member(id, name, email,password,member_status_id,member_type_id)
    }
  }
  
  // val mapping = {
  //   get[Pk[Long]]("id") ~
  //   get[String]("name") ~
  //   get[Option[String]]("email") ~
  //   get[String]("password")
  //   get[Int]("member_status_id") ~
  //   get[Int]("member_type_id") map {
  //     case id~name~email~password~member_status_id~member_type_id => Member(id, name, email,password,member_status_id,member_type_id)
  //   }
  // }

  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from member order by name").as(Member.mapping *).map(c => c.id.toString -> c.name)
  }

  def findByName(name: String): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where name = {name}").on('name -> name).as(mapping.singleOpt)
  }

  def findById(id: Long): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def findByCoopId(coopId: Long): List[Member] = DB.withConnection
  {
    implicit c => SQL("select m.* from member m, coop_member cm where cm.member_id = m.id and cm.coop_id = {coopId}").on('coopId -> coopId).as(mapping *)
  }

  def all(): List[Member] = DB.withConnection { implicit c =>
    SQL("select * from member").as(mapping *)
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
