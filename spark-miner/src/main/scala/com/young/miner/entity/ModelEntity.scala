package com.young.miner.entity

import org.codehaus.jackson.annotate.JsonIgnore

import scala.beans.BeanProperty

/**
  * Created by yangyong on 17-4-16.
  */
sealed trait  ModelEntity

case class Document(id:Int,text:String) extends ModelEntity

case class Word(id:Int,text:String,weight:Double) extends ModelEntity with Comparable[Word]{
  override def compareTo(o: Word): Int = -this.weight.compareTo(o.weight)
}

case class WordWeight(@BeanProperty name:String, @BeanProperty value:Double) extends ModelEntity with Comparable[WordWeight]{
  override def compareTo(o: WordWeight): Int = -this.value.compareTo(o.value)
}

