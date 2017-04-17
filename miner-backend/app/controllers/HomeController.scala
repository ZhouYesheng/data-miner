package controllers

import javax.inject._

import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  //有4种Action
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def echo = Action { implicit request =>
    //   Ok("get a request "+request)
    Status(200)("get a request " + request)
  }

  def result(id: Int) = Action {
    Result(header = ResponseHeader(200, Map.empty), body = HttpEntity.Strict(ByteString("Hello world!" + id), Some("text/plain")))
  }

  //重定向
  def baidu = Action(Redirect("https://www.baidu.com"))


}
