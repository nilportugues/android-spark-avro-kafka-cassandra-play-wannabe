package hr.fpopic.streaming

import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord

case class MyEvent2(item1: Int, item2: Int, a: Int, b: Int, c: Int, d: Int)

object MyEvent2 {
  val topic: String = "MyEvent2Topic"
  val schema: Schema = AvroSchema[MyEvent2]
  val format: RecordFormat[MyEvent2] = RecordFormat[MyEvent2]

  def fromObject(o: Object): MyEvent2 = format.from(o.asInstanceOf[GenericRecord])
}