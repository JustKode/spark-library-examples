package justkode.library.examples.event

case class Event(userId: Long,
                 eventId: Long,
                 timestamp: Long,
                 day: String,
                 hr: String,
                 validStatus: Long,
                 price: Double) {
}
