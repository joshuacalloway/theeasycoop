package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Member(id: Long, name: String)

object Member {
  
  val member = {
    get[Long]("id") ~
    get[String]("name") map {
      case id~name => Member(id, name)
    }
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
