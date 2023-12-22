package application.models

import core.models.Film
import java.time.LocalDateTime

class SessionInfo(val film: Film, val startingHour: LocalDateTime) {
}