package justkode.library.examples.runner

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkAppRunner {
  val sparkConf = new SparkConf().setMaster("local")

  // It uses S3 instead of HDFS
  sparkConf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
  sparkConf.set("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000")
  sparkConf.set("spark.hadoop.fs.s3a.path.style.access", "true")
  sparkConf.set("spark.hadoop.fs.s3a.access.key", "admin")
  sparkConf.set("spark.hadoop.fs.s3a.secret.key", "password")
  sparkConf.set("spark.hadoop.fs.s3a.connection.ssl.enabled", "true")
  sparkConf.set("spark.sql.warehouse.dir", "s3a://warehouse/")
  sparkConf.set("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
  sparkConf.set("spark.sql.catalog.s3", "org.apache.iceberg.spark.SparkSessionCatalog")
  sparkConf.set("spark.sql.catalog.s3.warehouse", "s3a://warehouse/")
  sparkConf.set("spark.sql.catalog.s3.type", "hadoop")


  val spark = SparkSession.builder().config(sparkConf).getOrCreate()
  val sc = spark.sparkContext
}
