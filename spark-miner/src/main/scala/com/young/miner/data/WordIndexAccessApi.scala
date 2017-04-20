package com.young.miner.data

import java.sql.{ResultSet, Statement, Connection}

import org.apache.commons.collections.bidimap.DualHashBidiMap

/**
  * Created by yangyong3 on 2017/4/19.
  */
trait WordIndexAccessApi {

  def getIndex(word: String): Option[Int]

  def saveWord(word: String): Option[Int]

  def getWordIndex(): DualHashBidiMap

  def close(con: Connection, stmt: Statement, rs: ResultSet): Unit = {
    if (con != null)
      con.close()
    if (stmt != null)
      stmt.close()
    if (rs != null)
      rs.close()
  }

}
