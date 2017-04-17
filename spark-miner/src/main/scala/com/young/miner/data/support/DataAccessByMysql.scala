package com.young.miner.data.support

import com.young.miner.data.DataAccessApi
import com.young.miner.db.DBConnection
import com.young.miner.entity.CommentLog
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{JdbcRDD, RDD}

/**
  * Created by yangyong on 17-4-16.
  */
class DataAccessByMysql(partitation: Int = 1) extends DataAccessApi {
  override def findCommentLog(sparkContext: SparkContext, commentType: String): RDD[CommentLog] = {
    val sql = "select * from scs_comment_log where score = " + commentType
    new JdbcRDD[CommentLog](sparkContext, DBConnection.getConnection, sql, 0, 0, partitation, rs => CommentLog(rs.getInt("id"), rs.getString("text")))
  }
}
