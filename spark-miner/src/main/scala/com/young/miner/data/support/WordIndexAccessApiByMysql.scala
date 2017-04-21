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
    val con = DBConnection.getInstance()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("select id from scs_word_index where word = '" + word + "'")
    var result : Option[Int] = Option.empty
    if (rs.next()) {
      result = Option(rs.getInt(1))
    } else {
      result = Option.empty
    }
    close(null,stmt,rs)
    result
  }

  override def saveWord(word: String): Option[Int] = {
    val con = DBConnection.getInstance()
    val ps = con.prepareStatement("insert into scs_word_index(word) values(?)", Statement.RETURN_GENERATED_KEYS)
    ps.setString(1, word)
    ps.executeUpdate()
    val rs = ps.getGeneratedKeys
    var result : Option[Int] = Option.empty
    if(rs.next()){
      result = Option(rs.getInt(1))
    }else{
      result = Option.empty
    }
    close(null,ps,rs)
    result
  }

  override def getWordIndex(): DualHashBidiMap = {
    val con = DBConnection.getInstance()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("select * from scs_word_index")
    val map = new DualHashBidiMap
    while (rs.next()) {
      map.put(rs.getString("word"), rs.getInt("id"))
    }
    close(null,stmt,rs)
    map
  }
}
