package hr.fpopic.streaming

import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import org.apache.avro.Schema

case class MyEvent(user: Int, date: String, item: Int, quantity: Double)

object MyEvent {

  val topic: String = "MyEventTopic"

  val schema: Schema = AvroSchema[MyEvent]

  val format: RecordFormat[MyEvent] = RecordFormat[MyEvent]

//  val schemaManual =
//    """{"type":"record",
//      |"name":"MyEvent",
//      |"namespace":"hr.fpopic.streaming",
//      |"fields":[
//      |{"name":"user","type":"int"},
//      |{"name":"date","type":"string"},
//      |{"name":"item","type":"int"},
//      |{"name":"quantity","type":"double"}
//      |]}""".stripMargin


  //  def formatFrom(value: Object): MyEvent = {
  //    val record = value.asInstanceOf[GenericData.Record]
  //    MyEvent(
  //      user = record.get("user").asInstanceOf[Int],
  //      date = record.get("date").toString,
  //      item = record.get("item").asInstanceOf[Int],
  //      quantity = record.get("quantity").asInstanceOf[Double]
  //    )
  //  }

  //  def formatTo(myEvent: MyEvent): GenericData.Record = {
  //    val record = new GenericData.Record(MyEvent.schema)
  //    record.put("user", myEvent.user)
  //    record.put("date", myEvent.date)
  //    record.put("item", myEvent.item)
  //    record.put("quantity", myEvent.quantity)
  //    record
  //  }

}