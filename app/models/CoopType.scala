package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class CoopType(id: Pk[Long], coop_type: String) extends AbstractModel {
  val name = coop_type
  def all = CoopType.all

  def save = Nil
  def update(id: Long) = Nil
  
}

object CoopType {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("coop_type") map {
      case id~coop_type => CoopType(id, coop_type)
    }
  }
     
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from coop_type order by coop_type").as(CoopType.mapping *).map(c => c.id.toString -> c.name)
  }

  def findById(id: Long): Option[CoopType] = DB.withConnection
  {
    implicit c => SQL("select * from coop_type where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def all(): List[CoopType] = DB.withConnection { implicit c =>
    SQL("select * from coop_type").as(mapping *)
                                           }
  def create(item: CoopType) {
    DB.withConnection { implicit c =>
      SQL("insert into coop_type (coop_type)").on(
        'coop_type -> item.coop_type
      ).executeUpdate()
                     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from coop_type where id = {id}").on(
        'id -> id
      ).executeUpdate()
                     }
  }

}
