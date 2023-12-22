package infrastructure.data.utils

import core.models.Film
import core.models.SeatType
import core.models.Session
import infrastructure.data.DataContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val numberPlaces = 5

fun seed(dataContext: DataContext): DataContext {
    val data = readJsonSessions(dataContext.connectionString + SESSION)

    if (data.isEmpty()) {
        generateInit(dataContext)
    }

    return dataContext
}

fun generateInit(dataContext: DataContext) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    clearJsonFile(dataContext.connectionString + SESSION)
    clearJsonFile(dataContext.connectionString + TICKET)
    clearJsonFile(dataContext.connectionString + FILM)

    val film1 = Film(1, 1, "Spider-man", "cool film", 9, 150)
    val film2 = Film(2, 2, "Dark Knight", "very interesting fim", 12, 210)
    val film3 = Film(3, 3, "Kolobok", "for children", 6, 150)
    val film4 = Film(4, 4, "Five night with Freddy", "horror", 4, 120)

    var inputDateTime = "2023-12-15 17:40:15"
    var dateTime = LocalDateTime.parse(inputDateTime, formatter)
    val session1 = Session(1, dateTime, MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })


    inputDateTime = "2023-12-23 15:15:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    val session2 = Session(2, dateTime, MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })


    inputDateTime = "2023-12-25 17:00:00"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    val session3 = Session(3, dateTime, MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })


    inputDateTime = "2023-12-27 16:00:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    val session4 = Session(4, dateTime, MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })

    var films = mutableListOf(film1, film2, film3, film4)
    var sessions = mutableListOf(session1, session2, session3, session4)
        .map { it -> createSessionJSON(it) }.toMutableList()
    dataContext.saveChangesFilms(films)
    dataContext.saveChangesSessions(sessions)
}