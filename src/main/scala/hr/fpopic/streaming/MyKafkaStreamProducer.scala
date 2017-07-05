package hr.fpopic.streaming

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object MyKafkaStreamProducer {

  def main(args: Array[String]): Unit = {

    val params = StreamingConfiguration.producerParams

    import collection.JavaConversions._

    val producer = new KafkaProducer[Object, Object](params)

    var r = 0

    while (true) {
      val myevent1 = MyEvent1(user = r, date = "ABCD", item = r, quantity = r)
      val myevent2 = MyEvent2(item1 = r, item2 = r, a = r, b = r, c = r, d = r)

      val record1 = new ProducerRecord[Object, Object](MyEvent1.topic, myevent1.user.toString, MyEvent1.format.to(myevent1))
      val record2 = new ProducerRecord[Object, Object](MyEvent2.topic, myevent2.item1.toString, MyEvent2.format.to(myevent2))

      producer.send(record1)
      producer.send(record2)

      println(s"Producing: $myevent1")
      println(s"Producing: $myevent2")

      Thread.sleep(2000)

      r += 1
    }

  }

}


