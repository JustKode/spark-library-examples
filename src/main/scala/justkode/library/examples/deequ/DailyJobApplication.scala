package justkode.library.examples.deequ

import justkode.library.examples.runner.SparkAppRunner
import org.apache.spark.sql.functions._
import org.joda.time.DateTime

object DailyJobApplication extends SparkAppRunner {
  def main(args: Array[String]): Unit = {
    val reportDate = new DateTime(2023, 1, 1, 0, 0)
    val dayStr = reportDate.toString("yyyyMMdd")
    val sourceDf = spark.read.parquet(s"s3a://warehouse/deequ/event/day=$dayStr")
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
      .parquet(s"s3a://warehouse/deequ/dailyReport/day=$dayStr")
  }
}
