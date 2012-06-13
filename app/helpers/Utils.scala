package helpers

import play.api.mvc.Session
import models.{Member,Coop}
import java.text.SimpleDateFormat

object Utils
{
  def isMember(s: Session, coop: Coop): Boolean = coop.isMember(getLoggedInUser(s))

  def isManager(s: Session, coop: Coop): Boolean = coop.isManager(getLoggedInUser(s))
    
  def isLogin(s: Session): Boolean = 
  { 
    s.get("username") match {
      case Some(x) if getLoggedInUser(s) != null => true
      case _ => false
    }
  }

  def getLoggedInUser(s: Session): Member =
    {
      s.get("username") match {
	case Some(x) => Member.findByEmail(x).getOrElse(null)
	case  _ => null
      }
    }

//  def getCurrentDate() : java.util.Date = new java.util.Date()
  def getCurrentDate() = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    dateFormat.format(new java.util.Date())
  }
}
