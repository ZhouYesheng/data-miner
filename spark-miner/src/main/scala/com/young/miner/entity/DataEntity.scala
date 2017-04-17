package com.young.miner.entity

/**
  * Created by yangyong on 17-4-16.
  */
sealed trait DataEntity

case class CommentLog(id: Int, comment: String, sentiment: Double = 0.0) extends DataEntity


