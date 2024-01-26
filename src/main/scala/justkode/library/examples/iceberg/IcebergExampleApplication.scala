package justkode.library.examples.iceberg

import justkode.library.examples.event.EventGenerator
import justkode.library.examples.runner.SparkAppRunner


object IcebergExampleApplication extends SparkAppRunner {
  def main(args: Array[String]): Unit = {
    // Make Table
    val firstDf = spark.createDataFrame(
      spark.sparkContext.parallelize(EventGenerator.getRandomEvents(10000)),
      EventGenerator.getSchema
    )

    firstDf.writeTo("s3.db.events").using("iceberg").createOrReplace()

    // First Snapshot
    val tempTs = System.currentTimeMillis().toString
    Thread.sleep(1000)

    // Appending Data
    val secondDf = spark.createDataFrame(
      spark.sparkContext.parallelize(EventGenerator.getRandomEvents(10000)),
      EventGenerator.getSchema
    )
    secondDf.writeTo("s3.db.events").append()

    // Show Group By Result of First Snapshot
    spark.read
      .option("as-of-timestamp", tempTs)
      .table("s3.db.events")
      .groupBy("userId")
      .count()
      .show()

    // Show Group By Result of Second Snapshot
    spark.read
      .table("s3.db.events")
      .groupBy("userId")
      .count()
      .show()
  }
}
