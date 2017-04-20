package com.young.miner.data

import com.young.miner.data.support.WordIndexAccessApiByMysql

/**
  * Created by yangyong3 on 2017/4/19.
  */
object WordIndexBox extends Serializable{

  val wordIndexAccess = new WordIndexAccessApiByMysql

  val wordIndexBox = wordIndexAccess.getWordIndex

  def getIndex(word: String): Int = {
    var index = wordIndexBox.get(word)
    if (index == null) {
      index = wordIndexAccess.saveWord(word)
      wordIndexBox.put(word, index)
    }
    index.asInstanceOf[Int]
  }

  def getWord(id: Int): String = {
    wordIndexBox.getKey(id).toString
  }
}
