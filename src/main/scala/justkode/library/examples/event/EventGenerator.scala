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
    Event(
      Random.nextLong(5) + 1L,
      Random.nextLong(5) + 1L,
      DateTime.now().minusDays(Random.nextInt(10)).toString("yyyy-MM-dd"),
      if (Random.nextLong(10) >= 1) 1 else 0,
      Random.nextDouble() * 10
    )
  }

  def getRandomEvents(n: Int): List[Row] = {
    val schema = Encoders.product[Event].schema
    List.tabulate(n)(i => new GenericRowWithSchema(getRandomEvent.productIterator.toSeq.toArray, schema))
  }
}
