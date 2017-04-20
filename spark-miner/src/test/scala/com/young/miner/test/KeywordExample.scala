package com.young.miner.test

import com.young.miner.data.WordIndexBox

/**
  * Created by yangyong3 on 2017/4/19.
  */
object KeywordExample {

  def main(args: Array[String]) {
    println(WordIndexBox.getIndex("我们"))
    println(WordIndexBox.getWord(1))
    val set = Set(1,2,3,5,6)

  }
}
