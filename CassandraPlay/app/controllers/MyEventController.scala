package controllers

import javax.inject.Inject

import conf.CassandraService
import models.MyEvent
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc.{MessagesRequest, _}
import tables.EventsDatabase

import scala.concurrent.ExecutionContext.Implicits.global

class MyEventController @Inject()(components: MessagesControllerComponents)
  extends MessagesAbstractController(components) {

  val db = new EventsDatabase(CassandraService.connector)

  // events
  def getEvents() = Action.async {
    db.getMyEvents.map { events =>
      Ok(views.html.events(events))
    }
  }

  // event

  val myEventForm = Form(
    mapping(
      "user" -> number,
      "item" -> number,
      "date" -> nonEmptyText,
      "quantity" -> of(doubleFormat)
    )(MyEvent.apply)(MyEvent.unapply))

  def index() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.event(myEventForm))
  }

  def addEvent() = Action { implicit request: MessagesRequest[AnyContent] =>
    myEventForm.bindFromRequest.fold(
      errorEventForm => {
        println(s"Bad: ${errorEventForm.data}")
        BadRequest(views.html.event(errorEventForm))
      },
      event => {
        db.saveMyEvent(event)
        println(s"Success: $event")
        Redirect(routes.MyEventController.getEvents())
      }
    )
  }


}