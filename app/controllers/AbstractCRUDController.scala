package controllers

import play.api.db._
import play.api.Play.current

import play.api._
import play.api.mvc._
import play.api.data.Form

import java.math.BigDecimal
import play.api.data._
import play.api.data.Forms._

import anorm._
import anorm.SqlParser._
import views._

import models.AbstractModel
import helpers.CustomFormats._

//abstract class AbstractForm[+T <: AbstractModel] extends Form[T] {
//  val mapping: play.api.data.Mapping[T]
//}

trait AbstractCRUDController extends Controller {
  type ModelType <: AbstractModel
  type FormType <: Form[ModelType]
  val form: FormType
  protected val listView = views.html.base.list(model_all)
  protected val indexView = "Your new application is ready."
  protected val showItemView = views.html.base.showItem
  protected val editItemView = views.html.base.editItem

//  protected def newItemView(form: FormType): play.api.mvc.SimpleResult[play.api.templates.Html]
  protected def model_delete(id: Long) 
  protected def model_all() : List[AbstractModel] 
  protected def model_findById(id: Long) : Option[AbstractModel] 


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

  def newItem: play.api.mvc.Action[play.api.mvc.AnyContent]

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
        Ok(listView)
      }
    )
          }


  def deleteItem(id: Long): play.api.mvc.Action[play.api.mvc.AnyContent] = Action {
    model_delete(id)
    Ok(listView)
  }

}
