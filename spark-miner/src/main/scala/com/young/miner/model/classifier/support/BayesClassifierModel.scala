package com.young.miner.model.classifier.support

import com.young.miner.model.classifier.ClassifierModel
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
class BayesClassifierModel(lambda: Double = 1.0, modelType: String = "multinomial") extends ClassifierModel[LabeledPoint] {

  private var model: NaiveBayesModel = model

  override def saveModel(sparkContext:SparkContext, modelPath: String): Unit = model.save(sparkContext, modelPath)

  override def readModel(sparkContext:SparkContext, modelPath: String): Unit = model = NaiveBayesModel.load(sparkContext, modelPath)

  override def training(data: RDD[LabeledPoint]) = model = NaiveBayes.train(data, lambda, modelType)

  override def predict(vector: Vector): Double = model.predict(vector)

  override def predict(vectors: RDD[Vector]): RDD[Double] = model.predict(vectors)
}
