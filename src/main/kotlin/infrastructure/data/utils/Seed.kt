package infrastructure.data.utils

import infrastructure.data.DataCinemaContext
import core.models.Film
import core.models.SeatType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun seed(dataCinemaContext: DataCinemaContext) : DataCinemaContext {
    var data = readAllFromJson(dataCinemaContext.connectionString)

    if (data.isEmpty()) {
        generateInit(dataCinemaContext)
    }

    dataCinemaContext.listOfSessions = data.map{ Mapper().toDomain(it)}.toMutableList()
    return dataCinemaContext
}

fun generateInit(dataCinemaContext: DataCinemaContext) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var inputDateTime = "2023-12-15 17:40:15"
    var dateTime = LocalDateTime.parse(inputDateTime, formatter)

    var seed = createSessionJSON(
        Film("Spider-man", "cool film", 9, 150),
        dateTime,
        MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })
    appendSessionToJson(seed, dataCinemaContext.connectionString)


    inputDateTime = "2023-12-23 15:15:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Dark Knight", "very interesting fim", 12, 210),
        dateTime,
        MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })
    appendSessionToJson(seed, dataCinemaContext.connectionString)


    inputDateTime = "2023-12-25 17:00:00"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Kolobok", "for children", 6, 150),
        dateTime,
        MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })
    appendSessionToJson(seed, dataCinemaContext.connectionString)


    inputDateTime = "2023-12-27 16:00:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Five night with Freddy", "horror", 4, 120),
        dateTime,
        MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })
    appendSessionToJson(seed, dataCinemaContext.connectionString)
}