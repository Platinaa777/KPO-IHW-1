package main.launch

import application.services.implementations.CinemaService
import infrastructure.data.DataContext
import infrastructure.data.utils.seed
import infrastructure.repositories.FilmRepositoryImpl
import infrastructure.repositories.SessionRepositoryImpl
import infrastructure.repositories.TicketRepositoryImpl
import main.utils.App

fun configure(): App {

    val newApp = App()

    val dbContext = seed(DataContext())

    newApp.addFilmRepository(FilmRepositoryImpl(dbContext))
    newApp.addSessionRepository(SessionRepositoryImpl(dbContext))
    newApp.addTicketRepository(TicketRepositoryImpl(dbContext))

    newApp.addCinemaService(
        CinemaService(
            newApp.getTicketRepository(),
            newApp.getSessionRepository(),
            newApp.getFilmRepository()
        )
    )

    return newApp
}

