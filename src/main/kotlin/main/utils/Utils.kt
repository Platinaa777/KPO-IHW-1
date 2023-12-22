package main.utils

import application.models.Actions
import application.models.FilmInfo
import application.models.SessionWithFilmData
import application.responses.TimeResponse
import application.services.interfaces.CinemaPartService
import core.models.Ticket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun printIntroduction() {
    println()
    println("Choose options:")
    println("1) Buy ticket for cinema")
    println("2) Return ticket")
    println("3) Look free and sold seats in cinema")
    println("4) Edit options of session in cinema (change time)")
    println("5) Change name of film")
    println("6) Change description of film")
    println("7) Add new session")
    println("8) Mark that the place is occupied by a visitor")
    println("9) Logout")
    println("10) Exit")
    println()
}

fun readSessionNumber(): Int {
    try {
        print("input number of session: ")
        return readln().toInt()
    } catch (_: Exception) {}
    return 0
}

fun printSessionInformation(currentSession: SessionWithFilmData) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    println("Session ID = ${currentSession.Id}\n" +
            "Film name = ${currentSession.name}\n" +
            "Description = ${currentSession.description}\n" +
            "Rating = ${currentSession.rating}\n" +
            "Duration = ${currentSession.durationMinutes}\n" +
            "Datetime = ${currentSession.startingHour.format(formatter)}\n")


    for (item in currentSession.seats) {
        item.forEach { it -> print("$it ") }
        println()
    }
}

fun getDataForSeat(cinemaService: CinemaPartService, action: Actions): Ticket? {
    val sessionNumber = readSessionNumber()

    var session = cinemaService.getAllPlacesForSession(sessionNumber)
    if (session == null) {
        println("Session not found")
        return null
    }
    var id = 0
    printSessionInformation(session)

    try {
        print("Input row of seat what you want to ${action.toString().lowercase()} ")
        val x = readln().toInt()
        print("Input column of seat what you want to ${action.toString().lowercase()} ")
        val y = readln().toInt()

        return Ticket(id, session.Id, x - 1, y - 1)
    } catch (e: Exception) { }

    return null
}

fun inputDateTime(): TimeResponse {
    val scanner = Scanner(System.`in`)
    print("Input date in format yyyy-MM-dd HH:mm:ss: ")
    val inputDateTime = scanner.nextLine()
    val response = TimeResponse(true);

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    try {
        val dateTime = LocalDateTime.parse(inputDateTime, formatter)
        response.dateTime = dateTime
    } catch (e: Exception) {
        response.isValid = false
    }
    return response
}

fun inputFilmInformation(): FilmInfo {
    print("Input name of film: ")
    var name = readln()

    print("Input description of film: ")
    var description = readln()

    var id = 0
    var rating = 0
    var duration = 0

    try {
        print("Input rating of film: ")
        rating = readln().toUInt().toInt()

        print("Input duration of film: ")
        duration = readln().toUInt().toInt()
    } catch (e: Exception) {
        return FilmInfo("", "", -1, -1)
    }

    return FilmInfo(name, description, rating, duration)
}