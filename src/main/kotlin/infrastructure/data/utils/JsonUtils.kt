package infrastructure.data.utils

import infrastructure.data.entities.SessionJSON
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import core.models.Film
import core.models.SeatType
import java.io.File
import java.time.LocalDateTime

const val numberPlaces = 5
fun readAllFromJson(connectionString: String): MutableList<SessionJSON> {
    val file = File(connectionString)
    val existingSessionsJson = file.readText()

    return try {
        Json.decodeFromString<MutableList<SessionJSON>>(existingSessionsJson)
    } catch (e: Exception) {
        mutableListOf()
    }
}

fun createSessionJSON(
    film: Film,
    inputDateTime: LocalDateTime,
    seats: MutableList<MutableList<SeatType>>
    ): SessionJSON {

    var date = inputDateTime.toString()
    if (checkIfDataEndsWithZeroZero(date)) {
        date += ":00"
    }

    val sessionJSON = SessionJSON(
        Film(film.name, film.description, film.rating, film.durationMinutes),
        date,
        seats
    )
    return sessionJSON
}

fun appendSessionToJson(session: SessionJSON, filePath: String) {
    val file = File(filePath)
    val existingSessionsJson = file.readText()

    var existingSessions : MutableList<SessionJSON> = mutableListOf()
    try {
        existingSessions = Json.decodeFromString<MutableList<SessionJSON>>(existingSessionsJson)
    } catch (e: Exception) {
    }

    existingSessions.add(session)
    val updatedSessionsJson = Json { prettyPrint = true }.encodeToString(existingSessions)
    file.writeText(updatedSessionsJson)
}

fun checkIfDataEndsWithZeroZero(stringDateTime: String) : Boolean {
    val index = stringDateTime.indexOfFirst { it == 'T' }

    return stringDateTime.length - index - 1 == 5
}


fun clearJsonFile(filePath: String) {
    val file = File(filePath)
    file.writeText("") // Записываем пустую строку в файл
}