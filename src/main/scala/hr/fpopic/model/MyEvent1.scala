package hr.fpopic.model

import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord

case class MyEvent1(userId: Int, itemId: Int, date: String, quantity: Double)

object MyEvent1 {
  val topic: String = "MyEvent1Topic"
  val schema: Schema = AvroSchema[MyEvent1]
  val format: RecordFormat[MyEvent1] = RecordFormat[MyEvent1]

  def fromObject(o: Object): MyEvent1 = format.from(o.asInstanceOf[GenericRecord])
}