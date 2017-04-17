package com.young.miner.data

import com.young.miner.entity.CommentLog
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
trait DataAccessApi extends Serializable {
  def findCommentLog(sparkContext: SparkContext, commentType: String): RDD[CommentLog]
}
