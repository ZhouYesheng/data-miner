package com.young.miner.model

/**
  * Created by yangyong on 17-4-16.
  */
class ModelException(message:String,throwable: Throwable) extends Exception{

  def this(message:String) = this(message,new Exception)

  def this(throwable: Throwable) = this("",throwable)
}
