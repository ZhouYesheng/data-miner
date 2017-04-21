package com.young.miner.data

import com.young.miner.data.support.WordIndexAccessApiByMysql

/**
  * Created by yangyong3 on 2017/4/19.
  */
object WordIndexBox extends Serializable {

  val wordIndexAccess = new WordIndexAccessApiByMysql

  val wordIndexBox = wordIndexAccess.getWordIndex

  def getIndex(word: String): Int = {
    var index = if (wordIndexBox.get(word) == null) 0 else wordIndexBox.get(word).asInstanceOf[Int]
    if (index == null || index == 0) {
      index = wordIndexAccess.saveWord(word).get
      wordIndexBox.put(word, index)
    }
    index
  }

  def getWord(id: Int): String = {
    if (wordIndexBox.getKey(id) == null) "None" else wordIndexBox.getKey(id).toString
  }
}
