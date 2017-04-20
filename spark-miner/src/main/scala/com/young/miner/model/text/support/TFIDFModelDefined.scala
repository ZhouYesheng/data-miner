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

  private var idf = Map[String, Double]()

  override def saveModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  private def document2word(document: Document): Seq[String] = {
    val terms = NlpAnalysis.parse(document.text).getTerms
    terms.map(term => term.getName)
  }


  private def tfidf(fenci: Seq[String]): Vector = {
    val seq = fenci.groupBy(word => word).map(kv => (WordIndexBox.getIndex(kv._1), kv._2.length * 1.0 * idf.getOrElse(kv._1, 0.0))).toSeq
    Vectors.sparse(seq.length, seq)
  }

  override def training(documents: RDD[Document]): Unit = {
    val doc_num = documents.count()
    val docNumMap = documents.map(document => document2word(document).toSet).flatMap(x => x).map((_, 1)).countByKey()
    idf = docNumMap.map(kv => (kv._1, Math.log10(doc_num / kv._2))).toMap
  }

  def tfidf(document: Document): Vector = {
    tfidf(document2word(document))
  }

  def tfidf(documents: RDD[Document]): RDD[(Int, Vector)] = {
    documents.map(document => (document.id, tfidf(document)))
  }
}
