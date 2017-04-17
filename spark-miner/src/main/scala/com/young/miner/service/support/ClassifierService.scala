package com.young.miner.service.support

import com.young.miner.entity.CommentLog
import com.young.miner.model.classifier.support.BayesClassifierModel
import com.young.miner.service.BaseService
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
/**
  * Created by yangyong on 17-4-16.
  */
class ClassifierService(document2VectorService: Document2VectorService) extends BaseService{

  protected val classifierModel = new BayesClassifierModel()

  def traingModel(comments:RDD[CommentLog]){
    val vectors = document2VectorService.text2Vectors(comments)
    classifierModel.training(vectors)
  }

  def classifier(vectors:RDD[(Int,Vector)]):RDD[(Int,Double)]={
      vectors.map(v=>(v._1,classifierModel.predict(v._2)))
  }
}
