package com.knol.core.impl

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import com.knol.core.Knolx
import com.knol.core.SessionRepo
import com.knol.db.connection.Connectiondb
import scala.util.control.Exception

class SessionImp extends Connectiondb with SessionRepo {

  val logger = LoggerFactory.getLogger(this.getClass)
  var id =4
  /**
   * This block is used for Insert the value in the Knol_x Table which contains the id,topic,s_date,knol_id.
   * knol_id is a foreign key which borrow here from the table knol.
   * This table we define id as an auto incremented so we don't pass the value of id.
   * We use "SELECT LAST_INSERT_ID()" for finding the id of inserted record.
   */

  def createS(knolx: Knolx): Option[Int] = {

    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          var qwery = "insert into knol_x(topic,s_date,knol_id) values (?,?,?)"
          val stmt = con.prepareStatement(qwery)
          stmt.setString(1, knolx.topic)
          stmt.setString(2, knolx.s_date)
          stmt.setInt(3, knolx.knol_id)
          stmt.executeUpdate()
          val query: String = "SELECT LAST_INSERT_ID();"
          val rs: ResultSet = stmt.executeQuery(query)
          rs.next()
          val re = rs.getInt(1)

          Some(1)
        case None => None
      }

    } catch {
      case ex: Exception =>
        None
    }

  }

  /**
   * This block is used for Update the value in the Knol_x Table which contains the id,topic,s_date,knol_id.
   * We pass the value of topic , s_date , knol_id and id on which we have to update the values.
   */

  def updateS(knolx: Knolx): Option[Int] = {
    var con = getConnection()
    try {
      con match {
        case Some(con) =>
          {
            var query = "update knol_x  SET topic = ? , s_date = ? , knol_id = ? where id = ?"
            val stmt = con.prepareStatement(query)
            stmt.setString(1, knolx.topic)
            stmt.setString(2, knolx.s_date)
            stmt.setInt(3, knolx.knol_id)
            stmt.setInt(id, knolx.id)
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
   * This block is used for Delete the value in the Knol_x Table.
   * We pass the value of id on which we have to Delete the values.
   */

  def deleteS(id: Int): Int = {
    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {

            var qwery = "delete from knol_x where id= ?"
            val stmt = con.prepareStatement(qwery)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            1
          }

        case None => 0
      }
    } catch {
      case ex: Exception =>
        0
    }
  }

  /**
   * This block is used for Retrieve the value in the Knol_x Table.
   * We use List Buffer for retrieve the element.
   * We  store the value in the list then retrieve it.
   */

  def getListS(): Option[List[Knolx]] = {
    var list = new ListBuffer[Knolx]
    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {
            var qwery = "select * from knol_x"
            val stmt = con.prepareStatement(qwery)
            val rs: ResultSet = stmt.executeQuery()
            while (rs.next()) {
              list += Knolx(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(id))
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
