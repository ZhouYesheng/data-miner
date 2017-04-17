package com.young.miner.entity

/**
  * Created by yangyong on 17-4-16.
  */
sealed trait  ModelEntity

case class Document(id:Int,text:String) extends ModelEntity
