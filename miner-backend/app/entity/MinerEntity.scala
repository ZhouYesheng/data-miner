package entity

import scala.beans.BeanProperty

/**
  * Created by yangyong on 17-4-22.
  */
case class Word(@BeanProperty name:String, @BeanProperty value:String)
