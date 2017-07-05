package hr.fpopic.streaming

import io.confluent.kafka.serializers.KafkaAvroDecoder
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object MySparkStreamConsumer {

  def processMyEvent1(event: MyEvent1): Unit = {
    println(s"Consuming: $event")
  }

  def processMyEvent2(event: MyEvent2): Unit = {
    println(s"Consuming: $event")
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.master("local[*]").getOrCreate
    val ssc = new StreamingContext(spark.sparkContext, Seconds(4))

    val params = StreamingConfiguration.consumerParams

    val myEvent1Stream = KafkaUtils
      .createDirectStream[Object, Object, KafkaAvroDecoder, KafkaAvroDecoder](ssc, params, Set(MyEvent1.topic))
      .mapValues(MyEvent1.fromObject)

    val myEvent2Stream = KafkaUtils
      .createDirectStream[Object, Object, KafkaAvroDecoder, KafkaAvroDecoder](ssc, params, Set(MyEvent2.topic))
      .mapValues(MyEvent2.fromObject)

    myEvent1Stream.foreachRDD { rdd =>
      rdd.foreachPartition { partition =>
        // do some cassandra work
        partition.foreach { case (_, event) =>
          processMyEvent1(event)
        }
      }
    }

    myEvent2Stream.foreachRDD { rdd =>
      rdd.foreachPartition { partition =>
        // do some cassandra work
        partition.foreach { case (_, event) =>
          processMyEvent2(event)
        }
      }
    }

    sys.ShutdownHookThread {ssc.stop(stopSparkContext = true, stopGracefully = true)}

    ssc.start()
    ssc.awaitTermination()
  }
}