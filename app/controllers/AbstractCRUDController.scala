package controllers

import anorm.SqlParser._
import anorm._
import helpers.CustomFormats._
import java.math.BigDecimal
import models.AbstractModel
import play.api.Play.current
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data._
import play.api.db._
import play.api.mvc._
import views._


//abstract class AbstractForm[+T <: AbstractModel] extends Form[T] {
//  val mapping: play.api.data.Mapping[T]
//}

// val mapping: Mapping[AbstractModel] = Nil
// val errors: Seq[FormError] = Nil
// val value: Option[AbstractModel] = Nil
// class AbstractForm[AbstractModel] extends Form(mapping, errors, value)

class AbstractForm[T <: AbstractModel](amapping: Mapping[T], adata: Map[String, String], aerrors: Seq[FormError], avalue: Option[T]) extends Form[T](amapping,adata, aerrors, avalue)

object AbstractForm
{
  def apply[T <: AbstractModel](mapping: Mapping[T]): Form[T] = new Form(mapping, Map.empty, Nil, None)

  // def apply[T](mapping: (String, Mapping[T])): AbstractForm[T] = AbstractForm(mapping._2.withPrefix(mapping._1), Map.empty, Nil, None)


}

trait AbstractCRUDController extends Controller {
  type ModelType <: AbstractModel
  type FormType <: AbstractForm[ModelType]
//  val form: FormType
  val form: Form[ModelType]

  protected def listView = {
    views.html.admin.base.list(model_all)
  }
  protected val indexView = "Your new application is ready."
  protected val showItemView = views.html.admin.base.showItem
  protected val editItemView = views.html.admin.base.editItem
  // protected val newItemView = views.html.base.newItem

//  protected def newItemView(form: FormType): play.api.mvc.SimpleResult[play.api.templates.Html]
  protected def model_delete(id: Long) 
  protected def model_all() : List[ModelType]
  protected def model_findById(id: Long) : Option[ModelType] 


  def list: play.api.mvc.Action[play.api.mvc.AnyContent] = Action {
    Ok(listView)
  }

  def index: play.api.mvc.Action[play.api.mvc.AnyContent] = Action {
    Ok(indexView)
  }

  def showItem(id: Long) : play.api.mvc.Action[play.api.mvc.AnyContent] =
    Action {
      val itemOption = model_findById(id)
      itemOption match {
      case None => Ok("no item exists")
      case Some(item) => {
        Ok(showItemView(item))
      }
      }
    }    
//  def applyViewMethod(method : (Form[ModelType])=>play.api.templates.Html, param: Form[ModelType]): play.api.templates.Html
  // = {
 //    method(param)
 //  } 
  def newItem : play.api.mvc.Action[play.api.mvc.AnyContent]

// play.api.mvc.Action[play.api.mvc.AnyContent]
  // = {
  //   Ok(applyViewMethod(newItemView.apply _, form))
  // }

  def saveItem: play.api.mvc.Action[play.api.mvc.AnyContent] = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => Ok("BadRequest(newItemView(formWithErrors))"),
      item => {
        item.save
        Ok(listView)
      }
    )}

  // def form_fill(item:model) : play.api.data.Form[model]

  def editItem(id: Long): play.api.mvc.Action[play.api.mvc.AnyContent]
//: play.api.mvc.Action[(play.api.mvc.Action[play.api.mvc.AnyContent], play.api.mvc.AnyContent)]
  // = Action {
  //   model_findById(id).map { item =>
  //     Ok(editItemView(id, form_fill(item)))
  //                         }.getOrElse(NotFound)
  // }

  def updateItem(id: Long): play.api.mvc.Action[play.api.mvc.AnyContent]
  = Action { implicit request =>
    form.bindFromRequest.fold(
      formWithErrors => Ok("BadRequest(view.editItem(id, formWithErrors))"),
      item => {
        item.update(id)
        Logger.info("updatedItem...")
        Ok(listView)
      }
    )
          }


  def deleteItem(id: Long): play.api.mvc.Action[play.api.mvc.AnyContent] = Action {
    model_delete(id)
    Ok(listView)
  }
}
