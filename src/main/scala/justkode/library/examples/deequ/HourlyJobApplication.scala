package justkode.library.examples.deequ

import justkode.library.examples.runner.SparkAppRunner
import org.apache.spark.sql.functions._
import org.joda.time.DateTime

object HourlyJobApplication extends SparkAppRunner{
  def main(args: Array[String]): Unit = {
    for (i <- 0 to 23) {
      val reportDate = new DateTime(2023, 1, 1, i, 0)
      val dayStr = reportDate.toString("yyyyMMdd")
      val hrStr = reportDate.toString("yyyyMMddHH")
      val sourceDf = spark.read.parquet(s"s3a://warehouse/deequ/event/day=$dayStr/hr=$hrStr")
      val report = sourceDf
        .filter(col("validStatus") === 1L)
        .groupBy(
          col("userId"),
          col("eventId")
        ).agg(
          sum(col("price")) as "price"
        )

      report.write
        .mode("overwrite")
        .parquet(s"s3a://warehouse/deequ/hourlyReport/day=$dayStr/hr=$hrStr")
    }
  }
}
