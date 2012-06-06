package helpers

import play.api.mvc.Session
import models.Member

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

}
