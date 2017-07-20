package hr.fpopic.conf

import org.apache.kafka.clients.consumer.ConsumerConfig.{AUTO_OFFSET_RESET_CONFIG, GROUP_ID_CONFIG}
import org.apache.kafka.clients.producer.ProducerConfig._

object StreamingConfiguration {

  private val kafka = "localhost:9092"
  private val zookeeper = "localhost:2181"
  private val schemaRegistry = "http://localhost:8081"

  private val SCHEMA_REGISTRY_URL = "schema.registry.url"
  private val ZOOKEEPER_CONNECT = "zookeeper.connect"
  private val METADATA_BROKER_LIST = "metadata.broker.list"

  val producerParams = Map(
    BOOTSTRAP_SERVERS_CONFIG -> kafka,
    SCHEMA_REGISTRY_URL -> schemaRegistry,
    RETRIES_CONFIG -> "0",
    ACKS_CONFIG -> "all",
    CLIENT_ID_CONFIG -> "ScalaProducerExample",
    KEY_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer",
    VALUE_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer"
  )

  val consumerParams = Map(
    ZOOKEEPER_CONNECT -> zookeeper,
    METADATA_BROKER_LIST -> kafka,
    SCHEMA_REGISTRY_URL -> schemaRegistry,
    AUTO_OFFSET_RESET_CONFIG -> "smallest",
    GROUP_ID_CONFIG -> "group1"
  )

}


