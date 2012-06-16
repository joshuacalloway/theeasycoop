package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class State(id: Pk[Long], name: String, code: String) extends AbstractModel {
  def all = State.all

  def save = Nil
  def update(id: Long) = Nil
  
}

object State {
  def UNKNOWN = new State(null, "UNKNOWN", "UNKKNOWN")
  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("code") map {
      case id~name~code => State(id, name, code)
    }
  }
     
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from state order by code").as(State.mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[State] = DB.withConnection
  {
    implicit c => SQL("select * from state where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[State] = DB.withConnection { implicit c =>
    SQL("select * from state").as(mapping *)
                                           }
  def create(item: State) {
    DB.withConnection { implicit c =>
      SQL("insert into state (name, code)").on(
        'name -> item.name,
	'code -> item.code
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from state where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
