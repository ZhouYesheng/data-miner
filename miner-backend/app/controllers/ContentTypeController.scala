package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}

/**
  * Created by yangyong on 17-4-12.
  */
@Singleton
class ContentTypeController @Inject extends Controller {

  def html = Action(Ok("<h1>hello world</h1>"))

  def text = Action(Ok("hello world"))

  def json = Action(Ok("""{name:"yangyong",age:32}""").as(JSON))

  def xml = Action(Ok("""<user><name>yangyong</name><age>32</age></user>""").as(XML))
}
