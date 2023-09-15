package justkode.library.examples.event

case class Event(userId: Long,
                 eventId: Long,
                 timestamp: String,
                 validStatus: Long,
                 price: Double) {
}
