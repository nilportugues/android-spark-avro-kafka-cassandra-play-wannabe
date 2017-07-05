package hr.fpopic.streaming

import io.confluent.kafka.serializers.KafkaAvroDecoder
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

object MyKafkaStreamProducer {

  def main(args: Array[String]): Unit = {

    val producer = new KafkaProducer[Object, Object](StreamingConfiguration.producerProps)

    var r = 0

    while (true) {

      val myevent = MyEvent(user = r, date = "ABCD", item = r, quantity = r)
      val record = new ProducerRecord[Object, Object](MyEvent.topic, MyEvent.format.to(myevent))

      producer.send(record)

      println(s"Producing: $myevent")
      Thread.sleep(2000)
      r += 1
    }

  }

}

object MySparkStreamConsumer {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.master("local[*]").getOrCreate

    val ssc = new StreamingContext(spark.sparkContext, Seconds(1))
    val params = StreamingConfiguration.consumerProps
    val topics = Set(MyEvent.topic)

    val stream = KafkaUtils
      .createDirectStream[Object, Object, KafkaAvroDecoder, KafkaAvroDecoder](ssc, params, topics)
      .map { case (key, value) => MyEvent.format.from(value.asInstanceOf[GenericRecord]) }

    stream.foreachRDD {
      _.foreach { myEvent =>
        processEvent(myEvent)
      }
    }

    sys.ShutdownHookThread {
      ssc.stop(stopSparkContext = true, stopGracefully = true)
    }

    ssc.start()
    ssc.awaitTermination()
  }

  def processEvent(event: MyEvent): Unit = {
    println(s"Consuming: $event")
  }

}
