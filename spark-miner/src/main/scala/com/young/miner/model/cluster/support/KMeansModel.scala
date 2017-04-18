package com.young.miner.model.cluster.support

import com.young.miner.entity.Document
import com.young.miner.model.cluster.ClusterModel
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-18.
  */
class KMeansModel extends ClusterModel[Document]{
  override def saveModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def readModel(sparkContext: SparkContext, modelPath: String): Unit = ???

  override def training(data: RDD[Document]): Unit = ???
}
