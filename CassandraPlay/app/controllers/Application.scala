package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._



class Application @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  def index = Action { implicit request: Request[_] =>
    Ok(views.html.index())
  }

}