package com.young.miner.model.text.support

import com.young.miner.data.WordIndexBox
import com.young.miner.entity.Document
import com.young.miner.model.text.TextModel
import org.ansj.splitWord.analysis.NlpAnalysis
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._

/**
  * Created by yangyong on 17-4-16.
  */
class TFIDFModelDefined(minDocFreq: Int = 2) extends TextModel[Document] {

  private var idf = Map[Int, Double]()

  override def saveModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  private def document2word(document: Document): Seq[String] = {
    val terms = NlpAnalysis.parse(document.text).getTerms
    terms.map(term => term.getName).filter(_.length<=10)
  }

  private def tfidf(fenci: Seq[String]): Map[Int,Double] = fenci.groupBy(word => word).map(kv => (WordIndexBox.getIndex(kv._1), kv._2.length * 1.0 * idf.getOrElse(WordIndexBox.getIndex(kv._1), 0.0)))


  override def training(documents: RDD[Document]): Unit = {
    val doc_num = documents.count()
    val docNumMap = documents.map(document => document2word(document).toSet).flatMap(x => x).map(x=>(WordIndexBox.getIndex(x), 1)).groupByKey().map(x=>(x._1, Math.log10(doc_num / x._2.size)))
    idf = docNumMap.collect().toMap
  }

  def tfidf(document: Document): Map[Int,Double] = {
    tfidf(document2word(document))
  }

  def tfidf(documents: RDD[Document]): RDD[(Int, Map[Int,Double])] = {
    documents.map(document => (document.id, tfidf(document)))
  }
}
