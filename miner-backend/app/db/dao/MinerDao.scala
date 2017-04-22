package db.dao

import db.DBConnection
import entity.Word

import scala.collection.mutable.ListBuffer

/**
  * Created by yangyong on 17-4-22.
  */
object MinerDao {

    def getKeywords(md5:String):Array[Word]={
      val con = DBConnection.getInstance()
      val stmt = con.createStatement()
      val rs = stmt.executeQuery("select wordtext as name,weight as value from scs_comment_keyword where md5 = '"+md5+"' order by weight desc")
      val list = ListBuffer[Word]()
      while(rs.next()){
        list.+=(Word(rs.getString(1),rs.getDouble(2).toString))
      }
      list.toArray
    }

  def main(args: Array[String]): Unit = {
    MinerDao.getKeywords("ed53e949acecdbc9b3ecd5564ff136bc").foreach(println _)
  }
}

