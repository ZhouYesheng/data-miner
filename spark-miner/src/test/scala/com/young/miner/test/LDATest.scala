package com.young.miner.test

import java.io.File

import com.young.miner.SparkTool
import com.young.miner.model.cluster.support.LDAModel

/**
  * Created by yangyong on 17-4-18.
  */
object LDATest {
  def main(args: Array[String]): Unit = {
    val ldatest = new LDAModel
    val path = LDATest.getClass().getResource("/").getPath()+"/data/"+"sample_lda_data.txt"
    ldatest.test(SparkTool.sparkContext,path)
  }

}
