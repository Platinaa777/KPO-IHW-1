package main.utils

import application.services.implementations.CinemaService
import core.repositories.FilmRepository
import core.repositories.SessionRepository
import core.repositories.TicketRepository
import infrastructure.data.DataContext

class App() {

    private var cinemaService: CinemaService? = null
    private var dbContext: DataContext? = null
    private var ticketRepository: TicketRepository? = null
    private var sessionRepository: SessionRepository? = null
    private var filmRepository: FilmRepository? = null

    fun addCinemaService(cinemaService: CinemaService) {
        this.cinemaService = cinemaService
    }

    fun addTicketRepository(ticketRepository: TicketRepository) {
        this.ticketRepository = ticketRepository
    }

    fun addSessionRepository(sessionRepository: SessionRepository) {
        this.sessionRepository = sessionRepository
    }

    fun addFilmRepository(filmRepository: FilmRepository) {
        this.filmRepository = filmRepository
    }

    fun addDbContext(dbContext: DataContext) {
        this.dbContext = dbContext
    }

    fun getDbContext(): DataContext {
        return dbContext!!
    }

    fun getCinemaService(): CinemaService {
        return cinemaService!!
    }

    fun getTicketRepository(): TicketRepository {
        return ticketRepository!!
    }

    fun getSessionRepository(): SessionRepository {
        return sessionRepository!!
    }

    fun getFilmRepository(): FilmRepository {
        return filmRepository!!
    }
}