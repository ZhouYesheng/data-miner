package com.young.miner.db

import java.sql.{Connection, DriverManager}

/**
  * Created by yangyong on 17-4-16.
  */
object DBConnection {

  Class.forName(DBConfig.driver)

  def getConnection(): Connection = {
    DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password)
  }
}
