package infrastructure.data.utils

import core.models.Session
import infrastructure.data.entities.SessionJSON
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Mapper() {

    fun toDomain(sessionJson: SessionJSON): Session {
        val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val formatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val startingHour = sessionJson.startingHour
        val dateTime = LocalDateTime.parse(startingHour, formatterInput)

        val formattedDateTime = dateTime.format(formatterOutput)

        return Session(sessionJson.Id, dateTime, sessionJson.seats)
    }
}