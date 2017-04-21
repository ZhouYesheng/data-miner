package com.young.miner.entity

/**
  * Created by yangyong on 17-4-16.
  */
sealed trait  ModelEntity

case class Document(id:Int,text:String) extends ModelEntity

case class Word(id:Int,text:String,weight:Double) extends ModelEntity with Comparable[Word]{
  override def compareTo(o: Word): Int = -this.weight.compareTo(o.weight)
}
