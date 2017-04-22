package com.young.miner.data

import java.sql.{Connection, ResultSet, Statement}

import com.young.miner.entity.{CommentLog, Word, WordWeight}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
trait DataAccessApi extends Serializable {
  def findCommentLog(sparkContext: SparkContext, commentType: String): RDD[CommentLog]

  def findCommentLog(md5:String):CommentLog

  def saveCommentKeywords(md5:String,keywords:Array[WordWeight])

  def deleteCommentKeywords(md5:String)

  def getKeywords(md5:String):Array[WordWeight]

  def close(con: Connection, stmt: Statement, rs: ResultSet): Unit = {
    if (con != null)
      con.close()
    if (stmt != null)
      stmt.close()
    if (rs != null)
      rs.close()
  }
}
