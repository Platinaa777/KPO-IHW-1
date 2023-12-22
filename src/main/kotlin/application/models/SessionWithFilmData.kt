package application.models

import core.models.SeatType
import java.time.LocalDateTime

class SessionWithFilmData(
    val Id: Int,
    var startingHour: LocalDateTime,
    var seats: MutableList<MutableList<SeatType>>,
    var name: String,
    var description: String,
    var rating: Int,
    var durationMinutes: Int
) {
}