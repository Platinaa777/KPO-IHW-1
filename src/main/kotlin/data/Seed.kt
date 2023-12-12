package data

import models.Film
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun seed(dataContext: DataContext) : DataContext {
    var data = readAllFromJson(dataContext.connectionString)

    if (data.isEmpty()) {
        generateInit(dataContext)
    }

    dataContext.listOfSessions = data.map{Mapper().map(it)}.toMutableList()
    return dataContext
}

fun generateInit(dataContext: DataContext) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var inputDateTime = "2023-12-15 12:10:50"
    var dateTime = LocalDateTime.parse(inputDateTime, formatter)

    var seed = createSessionJSON(
        Film("Spider-man", "cool film", 9, 150),
        dateTime)
    appendSessionToJson(seed, dataContext.connectionString)


    inputDateTime = "2023-12-19 15:15:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Dark Knight", "very interesting fim", 12, 210),
        dateTime)
    appendSessionToJson(seed, dataContext.connectionString)


    inputDateTime = "2023-12-23 17:00:00"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Kolobok", "for children", 6, 150),
        dateTime)
    appendSessionToJson(seed, dataContext.connectionString)


    inputDateTime = "2023-12-27 16:00:40"
    dateTime = LocalDateTime.parse(inputDateTime, formatter)
    seed = createSessionJSON(
        Film("Five night with Freddy", "horror", 4, 120),
        dateTime)
    appendSessionToJson(seed, dataContext.connectionString)
}