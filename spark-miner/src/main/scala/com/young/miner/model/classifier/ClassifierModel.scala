package com.young.miner.model.classifier

import com.young.miner.model.Model
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
trait ClassifierModel[DATA] extends Model[DATA]{

  def predict(vector: Vector):Double

  def predict(vectors:RDD[Vector]):RDD[Double]
}
