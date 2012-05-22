package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Coop(id: Long, name: String, description: String, manager: String)

object Coop {
  
  val coop = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[String]("manager") map {
      case id~name~description~manager => Coop(id, name, description, manager)
    }
  }

  def all(): List[Coop] = DB.withConnection { implicit c =>
    SQL("select * from coop").as(coop *)
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
