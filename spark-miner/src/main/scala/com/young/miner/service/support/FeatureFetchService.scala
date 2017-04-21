package com.young.miner.service.support

import java.util

import com.young.miner.SparkTool
import com.young.miner.data.WordIndexBox
import com.young.miner.data.support.DataAccessByMysql
import com.young.miner.entity.{Word, CommentLog, Document}
import com.young.miner.model.text.support.TFIDFModelDefined
import org.ansj.domain.Term
import org.ansj.splitWord.analysis.NlpAnalysis
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong3 on 2017/4/21.
  */
class FeatureFetchService {
  protected val tfidfModel = new TFIDFModelDefined

  def trainingModel(comments: RDD[CommentLog]): Unit = {
    val trainingData = comments.map(comment => Document(comment.id, comment.comment))
    tfidfModel.training(trainingData)
  }

  def saveKeyword(comments: RDD[CommentLog]): Unit = {
    val trainingData = comments.map(comment => Document(comment.id, comment.comment))
    trainingData.foreach(document => {
      val terms: util.List[Term] = NlpAnalysis.parse(document.text).getTerms()
      for (i <- 0 until terms.size()) {
        if (terms.get(i).getName.length <= 10)
          WordIndexBox.getIndex(terms.get(i).getName)
      }
    })
  }

  def getFeatures(comment: CommentLog): Array[Word] = tfidfModel.tfidf(Document(comment.id, comment.comment)).map(kv=>Word(0,WordIndexBox.getWord(kv._1),kv._2)).toArray.sorted

}

object FeatureFetchService {
  def main(args: Array[String]) {
    val dataApi = new DataAccessByMysql
    val trainingdata = dataApi.findCommentLog(SparkTool.sparkContext, null)

    println(trainingdata.count())
    val service = new FeatureFetchService
    service.saveKeyword(trainingdata)
    service.trainingModel(trainingdata)
    val comment = new CommentLog(1, "我们都是小黄豆啊")
    service.getFeatures(comment).foreach(println _)
  }
}
