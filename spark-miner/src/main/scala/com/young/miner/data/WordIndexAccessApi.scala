package com.young.miner.data

import java.sql.{ResultSet, Statement, Connection}

/**
  * Created by yangyong3 on 2017/4/19.
  */
trait WordIndexAccessApi {

  def getIndex(word: String): Option[Int]

  def saveWord(word: String): Option[Int]

  def getWordIndex(): scala.collection.mutable.Map[String, Int]

  def close(con: Connection, stmt: Statement, rs: ResultSet): Unit = {
    if (con != null)
      con.close()
    if (stmt != null)
      stmt.close()
    if (rs != null)
      rs.close()
  }

}
