package com.young.miner

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yangyong on 17-4-16.
  */
object SparkTool {

  private val conf = new SparkConf()
  conf.setAppName("young-miner")
  conf.setMaster("local[1]")
  val sparkContext = new SparkContext(conf)
}
