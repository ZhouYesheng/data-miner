package com.young.miner.model

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
trait Model[DATA] extends Serializable{

  def saveModel(sparkContext: SparkContext, modelPath: String)

  def readModel(sparkContext: SparkContext,modelPath: String)

  def training(data: RDD[DATA])

}