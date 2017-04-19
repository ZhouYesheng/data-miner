package com.young.miner.model.text.support

import com.young.miner.entity.Document
import com.young.miner.model.text.TextModel
import org.ansj.splitWord.analysis.NlpAnalysis
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.{HashingTF, IDF, IDFModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._

/**
  * Created by yangyong on 17-4-16.
  */
class TFIDFModelDefined(minDocFreq: Int = 2) extends TextModel[Document] {


  override def saveModel(sparkContext: SparkContext,modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext,modelPath: String): Unit = ???

  private def document2word(document: Document): Seq[String] = {
    val terms = NlpAnalysis.parse(document.text).getTerms
    terms.map(term=>term.getName)
  }

  private def tf(fenci:RDD[Seq[String]]):Vector={
     fenci.map(seq=>seq.groupBy(_).map(kv=>(kv._1.toString,kv._2.size*1.0)).toSeq)
  }

  override def training(documents: RDD[Document]): Unit = {
    val fenciData = documents.map(document=>document2word(document))
  }

  def tfidf(document: Document): Vector = {

  }

  def tfidf(documents: RDD[Document]): RDD[(Int, Vector)] = {

  }
}
