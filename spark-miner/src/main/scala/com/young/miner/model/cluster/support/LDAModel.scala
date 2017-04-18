package com.young.miner.model.cluster.support

import com.young.miner.entity.Document
import com.young.miner.model.cluster.ClusterModel
import com.young.miner.model.text.TextModel
import org.apache.spark.SparkContext
import org.apache.spark.mllib.clustering.LDA
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-18.
  */
class LDAModel extends ClusterModel[Document]{
  override def saveModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def training(data: RDD[Document]): Unit = ???

  def test(sparkContext: SparkContext,path:String): Unit ={
    val data = sparkContext.textFile(path)
    val parsedData = data.map(s => Vectors.dense(s.trim.split(' ').map(_.toDouble)))
    // Index documents with unique IDs
    val corpus = parsedData.zipWithIndex.map(_.swap).cache()

    // Cluster the documents into three topics using LDA
    val ldaModel = new LDA().setK(3).run(corpus)

    // Output topics. Each is a distribution over words (matching word count vectors)
    println("Learned topics (as distributions over vocab of " + ldaModel.vocabSize + " words):")
    val topics = ldaModel.topicsMatrix
    for (topic <- Range(0, 3)) {
      print("Topic " + topic + ":")
      for (word <- Range(0, ldaModel.vocabSize)) { print(" " + topics(word, topic)); }
      println()
    }
  }
}
