package com.young.miner.service.support

import java.io.File
import java.util

import com.young.miner.{MinerConfig, SparkTool}
import com.young.miner.data.WordIndexBox
import com.young.miner.data.support.DataAccessByMysql
import com.young.miner.entity.{CommentLog, Document, Word, WordWeight}
import com.young.miner.model.text.support.TFIDFModelDefined
import org.ansj.domain.Term
import org.ansj.splitWord.analysis.NlpAnalysis
import org.apache.commons.io.FileUtils
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.codehaus.jackson.map.ObjectMapper

/**
  * Created by yangyong3 on 2017/4/21.
  */
class FeatureFetchService {
  protected var tfidfModel: TFIDFModelDefined = null

  private val dataApi = new DataAccessByMysql

  private def trainingModel(sparkContext: SparkContext): Unit = {
    if (tfidfModel == null) {
      tfidfModel = new TFIDFModelDefined
      val comments = dataApi.findCommentLog(SparkTool.sparkContext, null)
      saveKeyword(sparkContext, comments)
      val trainingData = comments.map(comment => Document(comment.id, comment.comment))
      tfidfModel.training(trainingData)
    }
  }

  private def saveKeyword(sparkContext: SparkContext, comments: RDD[CommentLog]): Unit = {
    val trainingData = comments.map(comment => Document(comment.id, comment.comment))
    trainingData.foreach(document => {
      val terms: util.List[Term] = NlpAnalysis.parse(document.text).getTerms()
      for (i <- 0 until terms.size()) {
        if (terms.get(i).getName.length <= 10)
          WordIndexBox.getIndex(terms.get(i).getName)
      }
    })
  }

  private def getFeatures(sparkContext: SparkContext, comment: CommentLog, topn: Int): Array[WordWeight] = {
    trainingModel(sparkContext)
    val result = tfidfModel.tfidf(Document(comment.id, comment.comment)).map(kv => WordWeight(WordIndexBox.getWord(kv._1), kv._2)).toArray.sorted
    if (result.length > topn)
      result.splitAt(100)._1
    else
      result
  }

  private def getFeatures(sparkContext: SparkContext, md5: String, topn: Int = 100): Array[WordWeight] = {
    val result = dataApi.getKeywords(md5)
    if(result == null || result.isEmpty){
      val comment = dataApi.findCommentLog(md5)
      val keywords = getFeatures(sparkContext, comment, topn)
      dataApi.saveCommentKeywords(md5, keywords)
      return keywords
    }else{
      return result
    }
  }
}

object FeatureFetchService {

  private val service = new FeatureFetchService

  def getFeatures(sparkContext: SparkContext,md5:String,topn:Int = 100):Array[WordWeight] = {
      service.getFeatures(sparkContext,md5,topn)
  }

  def main(args: Array[String]): Unit = {
    //8565438dec61ba76cd85b955697de41b
    //ed53e949acecdbc9b3ecd5564ff136bc
    val mapper = new ObjectMapper()
    val keywords = FeatureFetchService.getFeatures(SparkTool.sparkContext,"ed53e949acecdbc9b3ecd5564ff136bc")
    val json = mapper.writeValueAsString(keywords)
    FileUtils.writeStringToFile(new File(MinerConfig.data_miner_keyword_path),json,"utf-8")
  }
}

