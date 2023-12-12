package core

import models.Film
import java.time.LocalDateTime

class SessionInfo(val film: Film, val startingHour: LocalDateTime) {
}