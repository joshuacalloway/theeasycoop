package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class MemberType(id: Pk[Long], member_type: String) extends AbstractModel {
  val name = member_type
  def all = MemberType.all

  def save = Nil
  def update(id: Long) = Nil
  
}

object MemberType {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("member_type") map {
      case id~member_type => MemberType(id, member_type)
    }
  }
  def REGULAR = findByName("REGULAR_MEMBER").get
     
  def MANAGER_ID = findByName("MANAGER").get.id
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from member_type order by member_type").as(MemberType.mapping *).map(c => c.id.toString -> c.name)
  }

  def findByName(name: String): Option[MemberType] = DB.withConnection
  {
    implicit c => SQL("select * from member_type where member_type = {name}").on('name -> name).as(mapping.singleOpt)
  }

  def findById(id: Long): Option[MemberType] = DB.withConnection
  {
    implicit c => SQL("select * from member_type where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[MemberType] = DB.withConnection { implicit c =>
    SQL("select * from member_type").as(mapping *)
                                           }
  def create(item: MemberType) {
    DB.withConnection { implicit c =>
      SQL("insert into member_type (member_type)").on(
        'member_type -> item.member_type
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from member_type where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
