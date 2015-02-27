package models

import java.util.Date
import java.sql.{ Date => SqlDate }
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.Tag
import java.sql.Timestamp
import org.joda.time.LocalDateTime

case class User(id: Option[Long] = None, name: String, address: String, email: String, password: String, phone: Long, usertype: Option[Long] = Some(2L), created: Option[Date] = Some(new java.util.Date), updated: Option[Date] = Some(new java.util.Date))
case class Signin(email: String, password: String)
class Users(tag: Tag) extends Table[User](tag, "USER") {

  implicit val util2sqlDateMapper = MappedColumnType.base[java.util.Date, java.sql.Date](
    { utilDate => new java.sql.Date(utilDate.getTime()) },
    { sqlDate => new java.util.Date(sqlDate.getTime()) })

  def id: Column[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name: Column[String] = column[String]("name", O.NotNull)
  def address: Column[String] = column[String]("address", O.NotNull)
  def email: Column[String] = column[String]("email", O.NotNull)
  def password: Column[String] = column[String]("password", O.NotNull)
  def phone: Column[Long] = column[Long]("phone", O.NotNull)
  def usertype: Column[Long] = column[Long]("usertype", O.Nullable, O.Default(2L))
  def created: Column[Date] = column[Date]("created", O.Nullable)
  def updated: Column[Date] = column[Date]("updated", O.Nullable)

  def * = (id.?, name, address, email, password, phone, usertype.?, created.?, updated.?) <> (User.tupled, User.unapply _) //scalastyle:ignore
  def uniqueEmail = index("IDX_EMAIL", email, unique = true)
}

object Users {

  val userquery = TableQuery[Users];

  /**
 * @param user
 * @param s
 * @return
 */
def insert(user: User)(implicit s: Session): Int = {
    // userquery.ddl.create
    userquery.insert(user)
  }

  /**
 * @param id
 * @param user
 * @param s
 * @return
 */
def update(id: Long, user: User)(implicit s: Session): Int = {
    val recordToUpdate: User = user.copy(Some(id))
    userquery.filter(_.id === id).update(recordToUpdate)
  }

  /**
 * @param id
 * @param s
 * @return
 */
def findById(id: Long)(implicit s: Session): Option[User] =
    userquery.filter(_.id === id).firstOption

  /**
 * @param email
 * @param s
 * @return
 */
def getData(email: String)(implicit s: Session): User = {
    val webUser = userquery.filter(_.email === email).first
    webUser
  }

  /**
 * @param signin
 * @param s
 * @return
 */
def signIn(signin: Signin)(implicit s: Session): Int = {
    val afftdRow = Query(userquery.filter(_.email === signin.email).filter(_.password === signin.password).length).first
    afftdRow
  }

}