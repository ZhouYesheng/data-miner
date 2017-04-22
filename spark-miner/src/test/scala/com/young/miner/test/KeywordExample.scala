package com.young.miner.test

import com.young.miner.data.WordIndexBox
import com.young.miner.entity.Word
import org.codehaus.jackson.map.ObjectMapper

/**
  * Created by yangyong3 on 2017/4/19.
  */
object KeywordExample {

  def main(args: Array[String]) {
    println(WordIndexBox.getIndex("我们"))
    println(WordIndexBox.getWord(1))


    val mapper = new ObjectMapper()
    val array = Array[Word](Word(1,"1",1.0))
    println(mapper.writeValueAsString(array))

  }
}
