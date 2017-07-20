package hr.fpopic.streaming

import hr.fpopic.conf.StreamingConfiguration
import hr.fpopic.model.MyEvent1
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.collection.JavaConversions._

object MyKafkaStreamProducer {

  def main(args: Array[String]): Unit = {

    val params = StreamingConfiguration.producerParams
    val producer = new KafkaProducer[Object, Object](params)

    var r = 0

    while (true) {
      val myevent1 = MyEvent1(userId = r, itemId = r, date = "ABCD", quantity = r)
      val record = new ProducerRecord[Object, Object](
        MyEvent1.topic, myevent1.userId.toString, MyEvent1.format.to(myevent1)
      )

      producer.send(record)
      println(s"Producing: $myevent1")

      Thread.sleep(2000)
      r += 1
    }
  }
}


