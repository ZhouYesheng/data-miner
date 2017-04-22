package controllers

import javax.inject.{Inject, Singleton}

import db.dao.MinerDao
import org.codehaus.jackson.map.ObjectMapper
import play.api.mvc.{Action, Controller}

/**
  * Created by yangyong on 17-4-22.
  */
@Singleton
class MinerController @Inject extends Controller{

  private val mapper = new ObjectMapper

  def keyword(md5:String) = Action(Ok(mapper.writeValueAsString(MinerDao.getKeywords(md5))).as(JSON))
}
