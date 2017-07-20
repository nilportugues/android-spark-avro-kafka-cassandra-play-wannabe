package hr.fpopic.conf

object SinkConfiguration {
  val HOST = "spark.cassandra.connection.host"
  val RPC_PORT = "spark.cassandra.connection.rpc.port"
  val NATIVE_PORT = "spark.cassandra.connection.native.port"

  val host = "127.0.0.1"
  val rpcPort = "9160"
  val nativePort = "9042"
}