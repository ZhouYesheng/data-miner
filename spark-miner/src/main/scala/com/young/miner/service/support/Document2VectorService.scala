package com.young.miner.service.support

import com.young.miner.entity.{CommentLog, Document}
import com.young.miner.model.text.support.TFIDFModel
import com.young.miner.service.BaseService
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

/**
  * Created by yangyong on 17-4-16.
  */
class Document2VectorService extends BaseService {

  protected var tfidfModel:TFIDFModel = null

  def trainingModel(comments: RDD[CommentLog]): Unit = {
    val trainingData = comments.map(comment => Document(comment.id, comment.comment))
    tfidfModel.training(trainingData)
  }

  def text2Vector(comments: RDD[CommentLog]): RDD[(Int, Vector)] = {
    if(tfidfModel == null){
      tfidfModel = new TFIDFModel
      tfidfModel.training(comments.map(x=>Document(x.id,x.comment)))
    }
    val documents = comments.map(comment => Document(comment.id, comment.comment))
    tfidfModel.tfidf(documents)
  }

  def text2Vectors(comments: RDD[CommentLog]): RDD[LabeledPoint] = {
    if(tfidfModel == null){
      tfidfModel = new TFIDFModel
      tfidfModel.training(comments.map(x=>Document(x.id,x.comment)))
    }
    comments.map(comment => LabeledPoint(comment.sentiment, tfidfModel.tfidf(Document(comment.id, comment.comment))))
  }
}
