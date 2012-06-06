package helpers

import play.api.mvc.Session

object Utils
{
  def isLogin(s: Session): Boolean = 
  { 
    s.get("username") match {
      case Some(x) => true
      case _ => false
    }
  }

}
