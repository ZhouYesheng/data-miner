package com.young.miner.test

import com.young.miner.SparkTool
import com.young.miner.data.support.DataAccessByMysql
import com.young.miner.entity.CommentLog
import com.young.miner.service.support.{ClassifierService, Document2VectorService}

/**
  * Created by yangyong on 17-4-16.
  */
object ClassifierTest {

  def main(args: Array[String]): Unit = {
//    val array = Array[CommentLog](CommentLog(1, "我们都是小黄豆",-1.0), CommentLog(2, "我们都是中国人",1.0),
//      CommentLog(3, "我们都是小黑豆",-1.0), CommentLog(4, "我们都是小垃圾",-1.0),CommentLog(5, "中国人们站起来了",1.0))
//    val commentLogs = SparkTool.sparkContext.parallelize(array)
    val dataApi = new DataAccessByMysql
    val commentLogs = dataApi.findCommentLog(SparkTool.sparkContext,null)
    val vectorService = new Document2VectorService()
    val classifierService = new ClassifierService(vectorService)
    vectorService.trainingModel(commentLogs)
    classifierService.traingModel(commentLogs)
    val predd = dataApi.findCommentLog(SparkTool.sparkContext,"1")
    val predata = vectorService.text2Vector(predd)
    val classifierResult = classifierService.classifier(predata)
    classifierResult.foreach(println _)
  }
}
