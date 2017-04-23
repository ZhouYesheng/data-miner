package com.young.miner.service.support

import com.young.miner.SparkTool
import com.young.miner.data.DataAccessApi
import com.young.miner.data.support.DataAccessByMysql
import com.young.miner.entity.CommentLog
import com.young.miner.model.classifier.support.BayesClassifierModel
import com.young.miner.service.BaseService
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
class ClassifierService extends BaseService {

  protected val classifierModel = new BayesClassifierModel()

  private val vectorService = new Document2VectorService()

  private def traingModel(comments: RDD[CommentLog]) {
    val vectors = vectorService.text2Vectors(comments)
    classifierModel.training(vectors)
  }

  private def classifier(vectors: RDD[(Int, Vector)]): RDD[(Int, Double)] = {
    vectors.map(v => (v._1, classifierModel.predict(v._2)))
  }

  private def classifiers(comments: RDD[CommentLog]): Map[Double, Double] = {
    val total = comments.count()
    val vectors = vectorService.text2Vector(comments)
    classifier(vectors).groupBy(_._2).map(kv => (kv._1, kv._2.size * 1.0)).collect().map(kv => (kv._1, kv._2 / total * 1.0)).toMap
  }
}

object ClassifierService {

  private val classifierService = new ClassifierService

  private val dataApi: DataAccessApi = new DataAccessByMysql

  private var tringing = true

  def traing(sparkContext: SparkContext): Unit = {
    val comments = dataApi.findCommentLog(sparkContext, null)
    classifierService.traingModel(comments)
    tringing = false
  }

  def classifier(sparkContext: SparkContext, md5: String): Map[Double, Double] = {
    if (tringing)
      traing(sparkContext)
    val comments = dataApi.findCommentLogByMd5(sparkContext, md5)
    classifierService.classifiers(comments)
  }

  def main(args: Array[String]): Unit = {
    val map = ClassifierService.classifier(SparkTool.sparkContext, args(0))
    map.foreach(println _)
  }
}
