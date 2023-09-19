package justkode.library.examples.deequ

import justkode.library.examples.event.EventGenerator
import justkode.library.examples.runner.SparkAppRunner

object EventCreateApplication extends SparkAppRunner {
  def main(args: Array[String]): Unit = {
      val events = EventGenerator.getRandomEvents(10000)
      val df = spark.createDataFrame(
        spark.sparkContext.parallelize(events),
        EventGenerator.getSchema
      )

      df.write
        .mode("overwrite")
        .partitionBy("day", "hr")
        .parquet("s3a://warehouse/deequ/event")
  }
}
