package com.knol.db.connection

import java.sql._
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
 * This trait is implemented for connection.
 * We define all the required info here.
 * Create a method getConnection for connection establishment.
 */

trait Connectiondb {
  val config = ConfigFactory.load()
  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")
  val driver = config.getString("db.driver")
  val log = LoggerFactory.getLogger(this.getClass)

  def getConnection(): Option[Connection] = {
    try {
      Class.forName(driver)
      val connection = DriverManager.getConnection(url, user, password)
      log.info("connection established", connection)
      Some(connection)
    } catch {
      case ex: Exception =>
       None
    }

  }

}
