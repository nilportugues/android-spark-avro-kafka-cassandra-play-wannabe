package tables

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import models.MyEvent

import scala.concurrent.Future

/**
  * Create the Cassandra representation of the 'events' keyspace
  */
class EventsDatabase(override val connector: CassandraConnection)
  extends Database[EventsDatabase](connector) {

  object myEventTable extends MyEventTable with connector.Connector

  def getMyEvents: Future[List[MyEvent]] = {
    myEventTable.getAllEvents
  }

  def saveMyEvent(myEvent: MyEvent): Future[ResultSet] = {
    myEventTable.store(myEvent).future()
  }

}


/**
  * Create the Cassandra representation of the MyEvent table
  */
abstract class MyEventTable extends Table[MyEventTable, MyEvent] {

  override def tableName: String = "myevent1"

  object userId extends IntColumn with PartitionKey with PrimaryKey
  object itemId extends IntColumn with PartitionKey with PrimaryKey
  object date extends StringColumn with PartitionKey with PrimaryKey
  object quantity extends DoubleColumn

  def getByUserId(userId: Int): Future[Option[MyEvent]] = {
    select
      .where(_.userId is userId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAllEvents: Future[List[MyEvent]] = {
    select.fetch()
  }

}
