package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger
import play.api.data.Forms._
import play.api.mvc._
import views._
import play.api.data._
import java.util.Date;

case class CoopMember(id: Pk[Long], coop_id: Int, joined_at: Date, member_id: Int, member_type_id: Int, member_status_id: Int)  {
  val name = null
}

object CoopMember {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[Int]("coop_id") ~
    get[Date]("joined_at") ~
    get[Int]("member_id") ~
    get[Int]("member_type_id") ~
    get[Int]("member_status_id") map {
      case id~coop_id~joined_at~member_id~member_type_id~member_status_id => CoopMember(id, coop_id, joined_at, member_id, member_type_id, member_status_id)
    }
  }
  def findById(id: Long): Option[CoopMember] = DB.withConnection
  {
    implicit c => SQL("select * from coop_member where id = {id}").on('id -> id).as(CoopMember.mapping.singleOpt)
  }

}
