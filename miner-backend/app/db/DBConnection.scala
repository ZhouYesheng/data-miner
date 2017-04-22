package db

import java.sql.{Connection, DriverManager}

/**
  * Created by yangyong on 17-4-16.
  */
object DBConnection {

  Class.forName(DBConfig.driver)

  private var con: Connection = null

  def getConnection(): Connection = {
    DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password)
  }

  def getInstance(): Connection = {
    if (con == null || con.isClosed)
      con = DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password)
    return con;

  }
}
