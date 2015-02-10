package com.knol.core.impl

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import com.knol.core._
import com.mysql.jdbc.PreparedStatement
import com.knol.db.connection.Connectiondb

class KnolRepoImpl extends Connectiondb with KnolRepo {

  var res = 0
  var done = 1
  val logger = LoggerFactory.getLogger(this.getClass)
  var id1=1
            var id2=2
            var id3=3
            var id4=4
  /**
   * This block is used for Insert the value in the Knol Table which contains the knol_id,name,email,mobno.
   * This table we define id as an auto incremented so we don't pass the value of id.
   * We use "SELECT LAST_INSERT_ID()" for finding the id of inserted record.
   */

  def create(knold: Knolder): Option[Int] = {

    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          var qwery = "insert into knol(name,email,mobno) values (?,?,?)"
          val stmt = con.prepareStatement(qwery)
          stmt.setString(1, knold.name)
          stmt.setString(2, knold.email)
          stmt.setInt(3, knold.mobno)
          stmt.executeUpdate()
          val query: String = "SELECT LAST_INSERT_ID();"
          val rs: ResultSet = stmt.executeQuery(query)
          rs.next()
          val re = rs.getInt(1)
          //logger.debug("Created id"+re)
          Some(1)
        case None => None
      }

    } catch {
      case ex: Exception =>
        None
    }

  }

  /**
   * This block is used for Update the value in the Knol Table which contains the knol_id,name,email,mobno.
   * We pass the value of topic , s_date , knol_id and id on which we have to update the values.
   */

  def update(knold: Knolder): Option[Int] = {

    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {
            var query = "UPDATE knol SET name = ? , email= ? , mobno = ? where knol_id=? "
            val stmt = con.prepareStatement(query)
            stmt.setString(id1,knold.name)
            stmt.setString(id2, knold.email)
            stmt.setInt(id3, knold.mobno)
            stmt.setInt(id4, knold.id)
            stmt.executeUpdate()
            Some(1)
          }
        case None => None
      }
    } catch {
      case ex: Exception =>
        None

    }

  }

  /**
   * This block is used for Delete the value in the Knol Table.
   * We pass the value of id on which we have to Delete the values.
   */

  def delete(id: Int): Int = {

    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {

            var qwery = "delete from knol where knol_id= ?"
            val stmt = con.prepareStatement(qwery)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            done
          }

        case None => res
      }
    } catch {
      case ex: Exception =>
        res
    }
  }

  /**
   * This block is used for Retrieve the value in the Knol Table.
   * We use List Buffer for retrieve the element.
   * We  store the value in the list then retrieve it.
   */

  def getList(): Option[List[Knolder]] = {

    var list = new ListBuffer[Knolder]
    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {
            var qwery = "select * from knol"
            val stmt = con.prepareStatement(qwery)
            val rs: ResultSet = stmt.executeQuery()
            while (rs.next()) {
              list += Knolder(rs.getString(id2), rs.getString(id3), rs.getInt(id4), rs.getInt(id1))
            }
            Some(list.toList)
          }
        case None => None
      }

    } catch {
      case ex: Exception =>
        None
    }
  }
}
