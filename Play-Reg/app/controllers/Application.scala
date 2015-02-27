package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms.date
import play.api.data.Forms.email
import play.api.data.Forms.nonEmptyText
import play.api.data.Forms.number
import play.api.data.Forms._
import play.api.db.slick._
import play.api.db.slick.dbSessionRequestAsSession
import play.api.Play.current
import play.api.data.Forms.tuple
import views._
import models._
import play.api.mvc.Flash

object Application extends Controller {

  val Home = Redirect(routes.Application.home())

  val registerForm: Form[User] = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "address" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "phone" -> longNumber,
      "usertype" -> default(optional(longNumber), Some(2L)),
      "created" -> default(optional(date("MM/dd/yyyy")), Some(new java.util.Date)),
      "updated" -> default(optional(date("MM/dd/yyyy")), Some(new java.util.Date)))(User.apply)(User.unapply))

  val signForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText)(Signin.apply)(Signin.unapply))

  /**
   * @logout from the Session
   */
  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You've been logged out")
  }

  /**
   * @Check the mail and password
   * @if match than login
   * @else error
   */
  def signin: Action[AnyContent] = DBAction { implicit rs =>
    try {

      val success = signForm.bindFromRequest.get
      val count = Users.signIn(success)
      if (count > 0) {
        println("if")
        Ok(views.html.regUser()).withSession("email" -> success.email)
      } else {
        Redirect(routes.Application.login).
          flashing("error" -> "Email or Password not found.")
      }
    } catch {
      case ex: Exception =>
        Ok(views.html.login(registerForm))
        Home.flashing("error" -> "insrt valide Email and Password.")
    }
  }

  /*registerForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(html.login(formWithErrors)) },
      signForm => {
        Ok(views.html.regUser())
        /*Users.insert(userreg)
        Home.flashing("success" -> "New %s has been Insert".format(userreg.name))*/
      })
    
    
  }*/

  /**
   * @call login page
   */
  def login: Action[AnyContent] = DBAction { implicit rs =>
    Ok(views.html.login(registerForm))
  }

  /**
   * @call create form
   */
  def create: Action[AnyContent] = DBAction { implicit rs =>
    Ok(views.html.form(registerForm))
  }

  /**
   * @save record in the database
   */
  def save: Action[AnyContent] = DBAction { implicit rs =>
    registerForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(html.form(formWithErrors)) },
      userreg => {
        Users.insert(userreg)
        Home.flashing("success" -> "New %s has been Insert".format(userreg.name))
      })
  }

  /**
   * @pass email an call the updation page
   * @return
   */
  def edit(email: String) = DBAction { implicit rs =>
    val existingData = Users.getData(email)
    Ok(html.editForm(email, registerForm.fill(existingData)))
  }

  /**
   * @pass email and update the record
   * @return
   */
  def update(email: String) = DBAction { implicit rs =>
    registerForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.editForm(email, formWithErrors)),
      user => {
        val userConn = Users.getData(email)
        userConn.id match {
          case Some(id: Long) => {
            val updatedUser = user.copy(id = Some(id), created = user.created)
            Users.update(id, updatedUser)
            Redirect(routes.Application.reg()).
              flashing("success" -> s"WebUser ${updatedUser.name} has been updated")
          }
        }
      })
  }

  /**
   * @call navigation bar
   */
  def navbar = Action {
    Ok(views.html.navbar())
  }

  /**
   * @call home page
   */
  def home = Action { implicit rs =>
    Ok(views.html.home())
  }

  /**
   * @call the contact page
   */
  def contact = Action { implicit rs =>
    Ok(views.html.contact())
  }

  /**
   * @call the register user page
   */
  def reg = Action { implicit rs =>
    Ok(views.html.regUser())

  }

  def callEdit = Action { implicit rs =>
    rs.session.get("email") match {
      case Some(email: String) => Redirect(routes.Application.edit(email))
    }
  }
}