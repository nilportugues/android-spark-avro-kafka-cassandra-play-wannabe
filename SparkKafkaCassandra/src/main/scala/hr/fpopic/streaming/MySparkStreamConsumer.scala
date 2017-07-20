package hr.fpopic.streaming

import com.datastax.spark.connector.streaming._
import hr.fpopic.conf.StreamingConfiguration
import hr.fpopic.model.MyEvent1
import io.confluent.kafka.serializers.KafkaAvroDecoder
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object MySparkStreamConsumer {

  def processMyEvent1(event: MyEvent1): MyEvent1 = {
    println(s"Consuming: $event")
    event
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.master("local[*]").getOrCreate
    val ssc = new StreamingContext(spark.sparkContext, Seconds(4))

    val params = StreamingConfiguration.consumerParams

    KafkaUtils
      .createDirectStream[Object, Object, KafkaAvroDecoder, KafkaAvroDecoder](ssc, params, Set(MyEvent1.topic))
      .map { case (_, v) => MyEvent1.fromObject(v) }
      .transform {_.map(processMyEvent1)}
      .saveToCassandra(keyspaceName = "events", tableName = "myevent1")

    sys.ShutdownHookThread {ssc.stop(stopSparkContext = true, stopGracefully = true)}

    ssc.start()
    ssc.awaitTermination()
  }
}