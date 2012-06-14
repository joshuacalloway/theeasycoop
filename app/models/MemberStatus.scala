package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class MemberStatus(id: Pk[Long], member_status: String) extends AbstractModel {
  val name = member_status
  def all = MemberStatus.all

  def save = Nil
  def update(id: Long) = Nil
  
}

object MemberStatus {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("member_status") map {
      case id~member_status => MemberStatus(id, member_status)
    }
  }
     
  def ACTIVE = findByName("ACTIVE").get
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from member_type order by member_status").as(MemberStatus.mapping *).map(c => c.id.toString -> c.name)
  }

  def findByName(name: String): Option[MemberStatus] = DB.withConnection
  {
    implicit c => SQL("select * from member_status where member_status = {name}").on('name -> name).as(mapping.singleOpt)
  }

  def findById(id: Long): Option[MemberStatus] = DB.withConnection
  {
    implicit c => SQL("select * from member_status where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[MemberStatus] = DB.withConnection { implicit c =>
    SQL("select * from member_status").as(mapping *)
                                           }
  def create(item: MemberStatus) {
    DB.withConnection { implicit c =>
      SQL("insert into member_status (member_status)").on(
        'member_status -> item.member_status
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from member_status where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
