package main

import infrastructure.auth.AuthServer
import application.models.Actions
import application.models.SessionInfo
import infrastructure.auth.entities.UserAuthOption
import infrastructure.auth.utils.activateAuthentication
import main.utils.*

fun main() {
    var app = App()
    // registry dependencies
    app = configure(app)

    println("Start program...\n")

    val cinemaService = app.getCinemaService()

    var authServer = AuthServer()

    var currentSession = UserAuthOption("","")

    var sessionNumber: Int = 0
    var choice = ""

    while (choice != "10") {

        currentSession = activateAuthentication(authServer, currentSession)

        printIntoduction()

        print("Push button - ")
        choice = readln()

        if (choice == "1") {
            val data = getDataForSeat(app.getCinemaService(), Actions.BUY)

            var resultFromServer = cinemaService.buyTicketForSession(data.sessionId, data.row, data.column)

            println(resultFromServer)
        } else if (choice == "2") {
            val data = getDataForSeat(app.getCinemaService(), Actions.RETURN)

            var resultFromServer = cinemaService.returnTicketForSession(data.sessionId, data.row, data.column)

            println(resultFromServer)
        } else if (choice == "3") {
            sessionNumber = readSessionNumber()
            var session = cinemaService.getAllPlacesForSession(sessionNumber)
            if (session == null) {
                println("Session does not exist")
                continue
            }
            printSessionInformation(session, sessionNumber)
        } else if (choice == "4") {
            sessionNumber = readSessionNumber()
            var data = inputDateTime()

            if (!data.isValid) {
                println("Wrong datetime")
                continue
            }

            var resultFromServer = cinemaService.editTimeOfSession(sessionNumber, data.dateTime!!)

            println(resultFromServer)
        } else if (choice == "5") {
            sessionNumber = readSessionNumber()
            print("Input new name: ")
            var text = readln()

            var resultFromServer = cinemaService.editNameOfFilm(sessionNumber, text)

            println(resultFromServer)
        } else if (choice == "6") {
            sessionNumber = readSessionNumber()
            print("Input new description: ")
            var text = readln()

            var resultFromServer = cinemaService.editDescriptionOfFilm(sessionNumber, text)

            println(resultFromServer)
        } else if (choice == "7") {
            val film = inputFilmInformation()
            if (film.rating == -1) {
                println("Invalid syntax")
                continue
            }
            val date = inputDateTime()

            if (!date.isValid) {
                println("Invalid datetime")
                continue
            }

            var response = cinemaService.addNewSession(SessionInfo(film, date.dateTime!!))

            if (response.isValid) {
                println("Successfully added session with id = ${response.sessionId}")
            } else {
                println("Session intercept some other session")
            }
        } else if (choice == "8") {
            val data = getDataForSeat(app.getCinemaService(), Actions.OCCUPY) // Mark that seat is occupied

            println(cinemaService.markSeatIsOccupied(data.sessionId, data.row, data.column))
        } else if (choice == "9") {
            println("Successfully log out")
            currentSession = UserAuthOption("","")
        } else if (choice == "10") {
            println("Exit....")
        } else {
            println("Command is not supported")
        }
    }
}