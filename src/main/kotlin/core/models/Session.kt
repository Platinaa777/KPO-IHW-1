package core.models

import java.time.LocalDateTime

class Session(
    val Id: Int,
    var startingHour: LocalDateTime,
    var seats: MutableList<MutableList<SeatType>>
) {
}
