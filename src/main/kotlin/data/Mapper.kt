package data

import models.Session
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Mapper() {

    fun map(sessionJSON: SessionJSON) : Session {
        val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val formatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val startingHour = sessionJSON.startingHour
        val dateTime = LocalDateTime.parse(startingHour, formatterInput)

        val formattedDateTime = dateTime.format(formatterOutput)

        return Session(sessionJSON.film, dateTime, sessionJSON.seats)
    }
}