package core.models

import java.time.LocalDateTime

class Session(val film: Film,
              var startingHour: LocalDateTime,
              var seats: MutableList<MutableList<SeatType>>) {
}
