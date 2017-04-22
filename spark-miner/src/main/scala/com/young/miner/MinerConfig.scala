package com.young.miner

import java.util.{Locale, ResourceBundle}

/**
  * Created by yangyong on 17-4-22.
  */
object MinerConfig {

  private val bundler = ResourceBundle.getBundle("config",Locale.getDefault())

  val data_miner_keyword_path = bundler.getString("data.miner.keyword.path")
}
