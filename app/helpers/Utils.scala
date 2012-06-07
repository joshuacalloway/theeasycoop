package helpers

import play.api.mvc.Session
import models.Member
import java.text.SimpleDateFormat

object Utils
{
  def isLogin(s: Session): Boolean = 
  { 
    s.get("username") match {
      case Some(x) => true
      case _ => false
    }
  }

  def getLoggedInUser(s: Session): Member =
    {
      Member.findByEmail(s.get("username").get).get
    }

//  def getCurrentDate() : java.util.Date = new java.util.Date()
  def getCurrentDate() = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    dateFormat.format(new java.util.Date())
  }
}
