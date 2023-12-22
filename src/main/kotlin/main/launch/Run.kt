package main.launch

import application.models.Actions
import application.models.SessionInfo
import infrastructure.auth.AuthServer
import infrastructure.auth.entities.UserAuthOption
import infrastructure.auth.utils.activateAuthentication
import main.utils.*

fun run(app: App) {
    println("Start program...\n")

    val cinemaService = app.getCinemaService()

    var authServer = AuthServer()

    var currentSession = UserAuthOption("", "")

    var sessionNumber: Int = 0
    var choice = ""

    while (choice != "10") {

        currentSession = activateAuthentication(authServer, currentSession)

        printIntroduction()

        print("Push button - ")
        choice = readln()

        if (choice == "1") {
            val ticket = getDataForSeat(app.getCinemaService(), Actions.BUY)

            if (ticket == null) {
                println("Ticket not found")
                continue
            }

            val resultFromServer = cinemaService.buyTicketForSession(ticket)

            println(resultFromServer)
        } else if (choice == "2") {
            var ticketId = 0
            try {
                print("Input id of ticket ")
                ticketId = readln().toInt()
            } catch (_: Exception) {
                println("\nInvalid ID\n")
                continue
            }

            val resultFromServer = cinemaService.returnTicketForSession(ticketId)

            println(resultFromServer)
        } else if (choice == "3") {
            sessionNumber = readSessionNumber()
            val session = cinemaService.getAllPlacesForSession(sessionNumber)

            if (session == null) {
                println("Session does not exist")
                continue
            }
            printSessionInformation(session)
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

            val response = cinemaService.addNewSession(SessionInfo(film, date.dateTime!!))
            println(response)
        } else if (choice == "8") {
            var ticketId = 0
            try {
                print("Input id of ticket ")
                ticketId = readln().toInt()
            } catch (_: Exception) {
                println("\nInvalid ID\n")
                continue
            }

            println(cinemaService.markSeatIsOccupied(ticketId))
        } else if (choice == "9") {
            println("Successfully log out")
            currentSession = UserAuthOption("", "")
        } else if (choice == "10") {
            println("Exit....")
        } else {
            println("Command is not supported")
        }
    }
}