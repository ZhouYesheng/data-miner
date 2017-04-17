package com.young.miner.model.text.support

import com.young.miner.entity.Document
import com.young.miner.model.text.TextModel
import org.ansj.splitWord.analysis.NlpAnalysis
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.{HashingTF, IDF, IDFModel}
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector
import collection.JavaConversions._
/**
  * Created by yangyong on 17-4-16.
  */
class TFIDFModel(minDocFreq: Int = 2) extends TextModel[Document] {

  private var idf: IDFModel = null

  private val hashingTF = new HashingTF()

  override def saveModel(sparkContext: SparkContext,modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext,modelPath: String): Unit = ???

  private def document2word(document: Document): Seq[String] = {
    val terms = NlpAnalysis.parse(document.text).getTerms
    terms.map(term=>term.getName)
  }

  override def training(documents: RDD[Document]): Unit = {
    val data = documents.map(document => document2word(document))
    val tf = hashingTF.transform(data)
    idf = new IDF(minDocFreq).fit(tf)
  }

  def tfidf(document: Document): Vector = {
    val tf = hashingTF.transform(document2word(document))
    idf.transform(tf)
  }

  def tfidf(documents: RDD[Document]): RDD[(Int, Vector)] = {
    val data = documents.map(document => (document.id, document2word(document)))
    val tfidfs = data.map(idvecotr => (idvecotr._1, idf.transform(hashingTF.transform(idvecotr._2))))
    tfidfs
  }
}
