package com.young.miner.data.support

import java.text.DecimalFormat

import com.young.miner.data.DataAccessApi
import com.young.miner.db.DBConnection
import com.young.miner.entity.{CommentLog, Word, WordWeight}
import org.apache.commons.lang.StringUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{JdbcRDD, RDD}

import scala.collection.mutable.ListBuffer

/**
  * Created by yangyong on 17-4-16.
  */
class DataAccessByMysql(partitation: Int = 1) extends DataAccessApi {

  private val  df = new DecimalFormat("#.00");

  override def findCommentLog(sparkContext: SparkContext, commentType: String): RDD[CommentLog] = {
    var sql = ""
    if (StringUtils.isBlank(commentType))
      sql = "select id,content,score from scs_comment_log  where ?=? "
    else
      sql = "select id,content,score from scs_comment_log where ?=? and score = " + commentType
    new JdbcRDD[CommentLog](sparkContext, DBConnection.getConnection, sql, 1, 1, partitation, rs => CommentLog(rs.getInt("id"), rs.getString("content"), 1.0 * rs.getInt("score")))
  }

  override def findCommentLog(md5: String): CommentLog = {
    val sb = new StringBuilder
    val con = DBConnection.getInstance()
    val stmt = con.createStatement()
    val sql = "select log.md5,log.content from scs_comment_log log,scs_comment_seed seed where log.md5 = seed.md5 and seed.md5 = '" + md5 + "'"
    val rs = stmt.executeQuery(sql)
    while (rs.next()) {
      sb.append(rs.getString("content") + ";")
    }
    close(null, stmt, rs)
    CommentLog(1, sb.toString())
  }

  override def saveCommentKeywords(md5: String, keywords: Array[WordWeight]): Unit = {
    val con = DBConnection.getInstance()
    val ps = con.prepareStatement("insert into scs_comment_keyword(md5,wordtext,weight) values (?,?,?)")
    for (word <- keywords) {
      ps.setString(1, md5)
      ps.setString(2, word.name)
      ps.setDouble(3, df.format(word.value).toDouble)
      ps.addBatch()
    }
    ps.executeBatch()
    close(null, ps, null)
  }

  override def deleteCommentKeywords(md5: String): Unit = {
    val con = DBConnection.getInstance()
    val stmt = con.createStatement()
    stmt.executeUpdate("delete from scs_comment_keyword where md5 = '" + md5 + "'")
    close(null, stmt, null)
  }

  override def getKeywords(md5:String):Array[WordWeight]={
    val con = DBConnection.getInstance()
    val stmt = con.createStatement()
    val rs = stmt.executeQuery("select wordtext as name,weight as value from scs_comment_keyword where md5 = '"+md5+"' order by weight desc")
    val list = ListBuffer[WordWeight]()
    while(rs.next()){
      list.+=(WordWeight(rs.getString(1),rs.getDouble(2)))
    }
    close(null,stmt,rs)
    list.toArray
  }
}

