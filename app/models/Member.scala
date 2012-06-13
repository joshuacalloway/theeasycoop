package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.Logger

case class Member(id: Pk[Long], name: String, email: String, password: String, cell: Option[String], address: String, state_id: Int, zip_code:String) extends AbstractModel {
  
  def save = {
    Member.save(this)
  }
  def update(id: Long) = {
    Member.update(id, this)
  }
  def all = Member.all
  def coops = Member.coops(id)
  def state = State.findById(state_id).get
}

object Member {

  val mapping = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[String]("email") ~
    get[String]("password") ~
    get[Option[String]]("cell") ~
    get[String]("address") ~
    get[Int]("state_id") ~
    get[String]("zip_code") map {
      case id~name~email~password~cell~address~state_id~zip_code => Member(id, name, email,password,cell,address,state_id,zip_code)
    }
  }
  
  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[Member] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from member where 
         email = {email} and password = {password}
        """
      ).on(
        'email -> email,
        'password -> password
      ).as(Member.mapping.singleOpt)
    }
  }
   
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from member order by name").as(Member.mapping *).map(c => c.id.toString -> c.name)
  }

  def optionsByCoopId(id:Long): Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select m.* from member m, coop_member cm where cm.member_id = m.id and cm.coop_id = {coop_id} order by name").on('coop_id -> id ).as(Member.mapping *).map(c => c.id.toString -> c.name)
  }

  def findByName(name: String): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where name = {name}").on('name -> name).as(mapping.singleOpt)
  }

  def findByEmail(email: String): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where email = {email}").on('email -> email).as(mapping.singleOpt)
  }

  def findById(id: Long): Option[Member] = DB.withConnection
  {
    implicit c => SQL("select * from member where id = {id}").on('id -> id).as(mapping.singleOpt)
  }

  def findByCoopId(coopId: Pk[Long]): List[Member] = DB.withConnection
  {
    implicit c => SQL("select m.* from member m, coop_member cm where cm.member_id = m.id and cm.coop_id = {coopId}").on('coopId -> coopId).as(mapping *)
  }

  def findByItemOrderId(id: Long): List[Member] = DB.withConnection
  {
    implicit c => SQL("select m.* from member m, itemorder_member bm where bm.member_id = m.id and bm.itemorder_id = {itemorder_id}").on('itemorder_id -> id).as(mapping *)
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
      SQL("insert into member (name, email, password, cell, address, state_id, zip_code) values ({name},{email},{password},{cell},{address},{state_id},{zip_code})").on(
        'name -> item.name,
        'email -> item.email,
        'password -> item.password,
        'cell -> item.cell,
        'address -> item.address,
	'state_id -> item.state_id,
	'zip_code -> item.zip_code
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

  def coops(id: Pk[Long]) : List[Coop] = {
    Coop.findByMemberId(id)
  }

  def update(id: Long, item: Member) {
    Logger.info("updating member : " + id + " with email : " + item.email)
    DB.withConnection { implicit c =>
      SQL("update member set name={name}, email={email}, password={password}, cell={cell}, address={address}, state_id={state_id}, zip_code={zip_code} where id={id}").on(
        'name -> item.name,
        'email -> item.email,
	'password -> item.password,
	'cell -> item.cell,
	'address -> item.address,
	'state_id -> item.state_id,
	'zip_code -> item.zip_code,
        'id -> id
      ).executeUpdate()
                     }
  }


}
