package main

import core.Actions
import core.SeatResponse
import core.TimeResponse
import data.DataContext
import data.seed
import models.Film
import repository.CinemaRepository
import services.CinemaService
import services.ICinemaService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner

fun printIntoduction() {
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

fun configure(App: App) : App {

    var newApp = App

    newApp.addCinemaRepository(CinemaRepository(seed(DataContext())))

    newApp.addCinemaService(
        CinemaService(newApp.getCinemaRepository())
    )

    return newApp
}

fun readSessionNumber() : Int {
    print("input number of session: ")
    return readln().toInt()
}

fun printSessionInformation(cinemaService: ICinemaService,
                            sessionNumber: Int) : Boolean {
    var currentSession = cinemaService.getAllPlacesForSession(sessionNumber)

    if (currentSession == null) {
        println("Session not found")
        return false
    }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    println("Film name = ${currentSession.film.name},\n" +
            "Description = ${currentSession.film.description}\n" +
            "Rating = ${currentSession.film.rating}\n" +
            "Duration = ${currentSession.film.durationMinutes}\n" +
            "Datetime = ${currentSession.startingHour.format(formatter)}\n" +
            "Cinema:")

    for (item in currentSession.seats) {
        item.forEach { it -> print("$it ") }
        println()
    }
    return true
}

fun getDataForSeat(cinemaService: ICinemaService, action: Actions) : SeatResponse{
    val sessionNumber = readSessionNumber()

    var result = printSessionInformation(cinemaService, sessionNumber)

    if (!result) {
        return SeatResponse(-1,-1,-1)
    }

    print("Input row of seat what you want to ${action.toString().lowercase()} ")
    var x = readln().toInt()
    print("Input column of seat what you want to ${action.toString().lowercase()} ")
    var y = readln().toInt()

    return SeatResponse(sessionNumber, x - 1, y - 1)
}

fun inputDateTime() : TimeResponse {
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

fun inputFilmInformation() : Film {
    print("Input name of film: ")
    var name = readln()

    print("Input description of film: ")
    var description = readln()

    var rating = 0;
    var duration = 0

    try {
        print("Input rating of film: ")
        rating = readln().toUInt().toInt()

        print("Input duration of film: ")
        duration = readln().toUInt().toInt()
    } catch (e: Exception) {
        return Film("","", -1,-1)
    }

    return Film(name, description, rating, duration)
}