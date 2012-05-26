package helpers

import play.api.data._
import play.api.data.format._

import java.math.BigDecimal
import play.api.data.format.Formats._
import play.api.data.Forms._


class BigDecimalWrapper(val value: String) {
  def toBigDecimal = new java.math.BigDecimal(value)
}

object CustomFormats
{

  implicit def moneyFormat: Formatter[Double] = new Formatter[Double] {

    override val format = Some("format.real", Nil)

    def bind(key: String, data: Map[String, String]) =
      parsing(_.toDouble, "error.real", Nil)(key, data)
    
    def unbind(key: String, value: Double) = Map(key -> value.toString)
  }

  private def parsing[T](parse: String => T, errMsg: String, errArgs: Seq[Any])(key: String, data: Map[String, String]): Either[Seq[FormError], T] = {
    stringFormat.bind(key, data).right.flatMap { s =>
      util.control.Exception.allCatch[T]
        .either(parse(s))
        .left.map(e => Seq(FormError(key, errMsg, errArgs)))
    }
  }


  implicit def wrapBigDecimal(i:String) = new BigDecimalWrapper(i)

  implicit def bigdecimalFormat: Formatter[BigDecimal] = new Formatter[BigDecimal] {

    override val format = Some("format.bigdecimal", Nil)

    def bind(key: String, data: Map[String, String]) =
      parsing(_.toBigDecimal, "error.real", Nil)(key, data)
    
    def unbind(key: String, value: BigDecimal) = Map(key -> value.toString)
  }


  implicit val money: Mapping[BigDecimal] = of[BigDecimal]

}
