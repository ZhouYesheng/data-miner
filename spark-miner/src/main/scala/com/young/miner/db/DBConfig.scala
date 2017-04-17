package com.young.miner.db

import java.util.{Locale, ResourceBundle}

/**
  * Created by yangyong on 17-4-16.
  */
object DBConfig {

  private val db = ResourceBundle.getBundle("db",Locale.getDefault)

  val url = db.getString("db.url")

  val username = db.getString("db.username")

  val password = db.getString("db.password")

  val driver = db.getString("db.driver")
}
