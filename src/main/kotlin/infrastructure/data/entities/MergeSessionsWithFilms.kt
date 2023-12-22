package infrastructure.data.entities

import java.time.LocalDateTime

class MergeSessionsWithFilms(
    var sessionId: Int,
    var startingHour: LocalDateTime,
    var durationMinutes: Int
) {
}