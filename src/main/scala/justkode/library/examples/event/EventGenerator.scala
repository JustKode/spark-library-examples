package justkode.library.examples.event

import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Encoders, Row}
import org.joda.time.DateTime

import scala.util.Random

object EventGenerator {
  def getSchema: StructType = {
    Encoders.product[Event].schema
  }

  def getRandomEvent: Event = {
    val reportDate = new DateTime(2023, 1, 1, 0, 0).plusMinutes(Random.nextInt(60 * 24))
    Event(
      Random.nextLong(5) + 1L,
      Random.nextLong(5) + 1L,
      reportDate.getMillis,
      reportDate.toString("yyyyMMdd"),
      reportDate.toString("yyyyMMddHH"),
      if (Random.nextLong(10) >= 1) 1 else 0,
      Random.nextDouble() * 10
    )
  }

  def getRandomEvents(n: Int): List[Row] = {
    val schema = Encoders.product[Event].schema
    List.tabulate(n)(i => new GenericRowWithSchema(getRandomEvent.productIterator.toSeq.toArray, schema))
  }
}
