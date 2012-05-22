package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Coop(id: Long, name: String, manager: Long)

object Coop {
  
  val coop = {
    get[Long]("id") ~
    get[String]("name") ~
    get[Long]("manager") map {
      case id~name~manager => Coop(id, name, manager)
    }
  }

  def all(): List[Coop] = DB.withConnection { implicit c =>
    SQL("select * from coop").as(coop *)
                                           }

  def create(name: String, manager : String) {
    DB.withConnection { implicit c =>
      SQL("insert into Coop (name, manager_id) values ({name}, 1)").on(
        'name -> name
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
