package conf

import com.outworkers.phantom.dsl._
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object CassandraService {
  private val conf = ConfigFactory.parseResources("cassandra.conf").getConfig("cassandra")
  private val port = conf.getInt("port")
  private val hosts = conf.getStringList("hosts").asScala
  private val keyspace = conf.getString("keyspace")

  lazy val connector = ContactPoints(hosts, port).keySpace(keyspace)
}