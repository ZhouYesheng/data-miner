package com.young.miner.data.support

import java.sql.Statement

import com.young.miner.data.WordIndexAccessApi
import com.young.miner.db.DBConnection
import org.apache.commons.collections.bidimap.DualHashBidiMap

/**
  * Created by yangyong3 on 2017/4/19.
  */
class WordIndexAccessApiByMysql extends WordIndexAccessApi {
  override def getIndex(word: String): Option[Int] = {
    val con = DBConnection.getConnection()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("select id from scs_word_index where word = '" + word + "'")
    if (rs.next()) {
      Option(rs.getInt(1))
    } else {
      Option.empty
    }
  }

  override def saveWord(word: String): Option[Int] = {
    val con = DBConnection.getConnection()
    val ps = con.prepareStatement("insert into scs_word_index(word) values(?)", Statement.RETURN_GENERATED_KEYS)
    ps.setString(1, word)
    ps.executeUpdate()
    val rs = ps.getGeneratedKeys
    if(rs.next()){
      Option(rs.getInt(1))
    }else{
      Option.empty
    }
  }

  override def getWordIndex(): DualHashBidiMap = {
    val con = DBConnection.getConnection()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("select * from scs_word_index")
    val map = new DualHashBidiMap
    while (rs.next()) {
      map.put(rs.getString("word"), rs.getInt("id"))
    }
    map
  }
}
