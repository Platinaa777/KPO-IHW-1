package application.services.implementations

import application.models.SessionInfo
import application.models.SessionWithFilmData
import application.responses.ResponseType
import application.services.handlers.ResponseDataHandler
import application.services.interfaces.CinemaPartService
import application.services.interfaces.TicketService
import core.models.Ticket
import core.repositories.FilmRepository
import core.repositories.SessionRepository
import core.repositories.TicketRepository
import java.time.LocalDateTime

class CinemaService(
    private val ticketRepository: TicketRepository,
    private val sessionRepository: SessionRepository,
    private val filmRepository: FilmRepository) : TicketService, CinemaPartService {
    override fun getAllPlacesForSession(sessionId: Int): SessionWithFilmData? {
        return sessionRepository.getAllSeatsForSession(sessionId)
    }

    override fun editTimeOfSession(sessionId: Int, newTime: LocalDateTime): String {
        val films = filmRepository.getAllFilms()

        return ResponseDataHandler(sessionRepository.changeTimeForSession(sessionId, newTime, films)).getResult()
    }

    override fun addNewSession(session: SessionInfo): String {
        val films = filmRepository.getAllFilms()

        return ResponseDataHandler(sessionRepository.addSession(session, films)).getResult()
    }

    override fun editNameOfFilm(filmId: Int, newName: String): String {
        return ResponseDataHandler(filmRepository.changeNameOfFilm(filmId, newName)).getResult()
    }

    override fun editDescriptionOfFilm(filmId: Int, newDescription: String): String {
        return ResponseDataHandler(filmRepository.changeDescriptionOfFilm(filmId, newDescription)).getResult()
    }

    override fun markSeatIsOccupied(ticketId: Int): String {
        val ticket = ticketRepository.getTicketById(ticketId)
        if (ticket == null) {
            return ResponseDataHandler(ResponseType.TICKET_IS_NOT_EXIST).getResult()
        }

        val session = sessionRepository.getSessionById(ticket.sessionId)
        val sessions = sessionRepository.getAllSessions()

        if (session == null) {
            return ResponseDataHandler(ResponseType.SESSION_NOT_EXIST).getResult()
        }

        return ResponseDataHandler(ticketRepository.markTicketIsUsed(ticket, session, sessions)).getResult()
    }

    override fun buyTicketForSession(ticket: Ticket): String {
        val ticketInDb = ticketRepository.getTicketById(ticket.id)
        if (ticketInDb != null) {
            return ResponseDataHandler(ResponseType.CANT_BOUGHT_TICKET).getResult()
        }

        val sessions = sessionRepository.getAllSessions()

        val response = ticketRepository.buyTicket(ticket, sessions)
        var ticketId = " TICKET ID =  " + response.ticketId
        if (response.ticketId == -1) {
            ticketId = ""
        }

        return ResponseDataHandler(response.responseType).getResult() + ticketId
    }

    override fun returnTicketForSession(ticketId: Int): String {
        val ticketInDb = ticketRepository.getTicketById(ticketId)

        if (ticketInDb == null) {
            return ResponseDataHandler(ResponseType.TICKET_IS_NOT_EXIST).getResult()
        }

        val sessions = sessionRepository.getAllSessions()

        return ResponseDataHandler(ticketRepository.returnTicket(ticketInDb, sessions)).getResult()
    }
}