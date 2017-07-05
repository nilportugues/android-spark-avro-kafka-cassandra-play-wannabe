package hr.fpopic.streaming

import org.apache.kafka.clients.consumer.ConsumerConfig.{AUTO_OFFSET_RESET_CONFIG, GROUP_ID_CONFIG}
import org.apache.kafka.clients.producer.ProducerConfig._

object StreamingConfiguration {

  val KAFKA = "localhost:9092"
  val ZOOKEEPER = "localhost:2181"
  val SCHEMA_REGISTRY = "http://localhost:8081"

  private[this] val SCHEMA_REGISTRY_URL = "schema.registry.url"
  private[this] val ZOOKEEPER_CONNECT = "zookeeper.connect"
  private[this] val METADATA_BROKER_LIST = "metadata.broker.list"

  val producerParams = Map(
    BOOTSTRAP_SERVERS_CONFIG -> KAFKA,
    SCHEMA_REGISTRY_URL -> SCHEMA_REGISTRY,
    RETRIES_CONFIG -> "0",
    ACKS_CONFIG -> "all",
    CLIENT_ID_CONFIG -> "ScalaProducerExample",
    KEY_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer",
    VALUE_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer"
  )

  val consumerParams = Map(
    ZOOKEEPER_CONNECT -> ZOOKEEPER,
    METADATA_BROKER_LIST -> KAFKA,
    SCHEMA_REGISTRY_URL -> SCHEMA_REGISTRY,
    AUTO_OFFSET_RESET_CONFIG -> "smallest",
    GROUP_ID_CONFIG -> "group1"
  )

}


