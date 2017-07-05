package hr.fpopic.streaming

import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig.{AUTO_OFFSET_RESET_CONFIG, GROUP_ID_CONFIG}
import org.apache.kafka.clients.producer.ProducerConfig._

object StreamingConfiguration {

  private[this] val SCHEMA_REGISTRY_URL = "schema.registry.url"
  private[this] val ZOOKEEPER_CONNECT = "zookeeper.connect"
  private[this] val METADATA_BROKER_LIST = "metadata.broker.list"

  val producerProps: Properties = {
    val props = new java.util.Properties
    props.put(RETRIES_CONFIG, "0")
    props.put(ACKS_CONFIG, "all")
    props.put(CLIENT_ID_CONFIG, "ScalaProducerExample")
    props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(SCHEMA_REGISTRY_URL, "http://localhost:8081")
    props.put(KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props
  }

  val consumerProps: Map[String, String] = Map(
    METADATA_BROKER_LIST -> "localhost:9092",
    ZOOKEEPER_CONNECT -> "localhost:2181",
    SCHEMA_REGISTRY_URL -> "http://localhost:8081",
    AUTO_OFFSET_RESET_CONFIG -> "smallest",
    GROUP_ID_CONFIG -> "group1"
  )

}


