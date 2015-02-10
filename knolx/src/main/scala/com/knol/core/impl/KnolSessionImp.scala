package com.knol.core.impl

import java.sql.ResultSet

import scala.collection.mutable.ListBuffer

import org.slf4j.LoggerFactory

import com.knol.core.KnolSession
import com.knol.core.KnolSessionRepo
import com.knol.db.connection.Connectiondb

class KnolSessionImp extends Connectiondb with KnolSessionRepo {

  val logger = LoggerFactory.getLogger(this.getClass)
  def retreive(value: Int): Option[List[KnolSession]] = {
    var list = new ListBuffer[KnolSession]
    val con = getConnection()
    try {
      con match {
        case Some(con) =>
          {

            var query = "select x.id,x.topic,x.s_date,x.knol_id,k.name,k.email,k.mobno from knol_x x,knol k where k.knol_id=x.knol_id AND k.knol_id = " + value
            val stmt = con.prepareStatement(query)
            val rs: ResultSet = stmt.executeQuery()
            while (rs.next()) {
              list += KnolSession(rs.getInt("id"), rs.getString("topic"), rs.getString("s_date"), rs.getInt("knol_id"), rs.getString("name"))
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
