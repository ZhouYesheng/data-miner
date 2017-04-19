package com.young.miner.data.support

import com.young.miner.data.DataAccessApi
import com.young.miner.db.DBConnection
import com.young.miner.entity.CommentLog
import org.apache.commons.lang.StringUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{JdbcRDD, RDD}

/**
  * Created by yangyong on 17-4-16.
  */
class DataAccessByMysql(partitation: Int = 1) extends DataAccessApi {
  override def findCommentLog(sparkContext: SparkContext, commentType: String): RDD[CommentLog] = {
    var sql = ""
    if(StringUtils.isBlank(commentType))
      sql = "select id,content,score from scs_comment_log  where ?=? and (score = 4 or score = 5)"
    else
      sql = "select id,content,score from scs_comment_log where ?=? and score = " + commentType
    new JdbcRDD[CommentLog](sparkContext, DBConnection.getConnection, sql, 1, 1, partitation, rs => CommentLog(rs.getInt("id"), rs.getString("content"),1.0*rs.getInt("score")))
  }
}
