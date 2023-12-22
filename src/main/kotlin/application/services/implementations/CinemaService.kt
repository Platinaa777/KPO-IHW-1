package application.services.implementations

import application.models.SessionInfo
import application.models.SessionWithFilmData
import application.services.handlers.ResponseDataHandler
import application.services.interfaces.CinemaPartService
import application.services.interfaces.TicketService
import core.models.Ticket
import core.repositories.FilmRepository
import core.repositories.SessionRepository
import core.repositories.TicketRepository
import java.time.LocalDateTime

class CinemaService(
    val ticketRepository: TicketRepository,
    val sessionRepository: SessionRepository,
    val filmRepository: FilmRepository) : TicketService, CinemaPartService {
    override fun getAllPlacesForSession(sessionId: Int): SessionWithFilmData? {
        var session = sessionRepository.getAllSeatsForSession(sessionId)
        return session
    }

    override fun buyTicketForSession(ticket: Ticket): String {
        return ResponseDataHandler(ticketRepository.buyTicket(ticket)).getResult();
    }

    override fun returnTicketForSession(ticketId: Int): String {
        return ResponseDataHandler(ticketRepository.returnTicket(ticketId)).getResult()
    }

    override fun editTimeOfSession(sessionId: Int, newTime: LocalDateTime): String {
        return ResponseDataHandler(sessionRepository.changeTimeForSession(sessionId, newTime)).getResult()
    }

    override fun editNameOfFilm(filmId: Int, newName: String): String {
        return ResponseDataHandler(filmRepository.changeNameOfFilm(filmId, newName)).getResult()
    }

    override fun editDescriptionOfFilm(filmId: Int, newDescription: String): String {
        return ResponseDataHandler(filmRepository.changeDescriptionOfFilm(filmId, newDescription)).getResult()
    }

    override fun addNewSession(session: SessionInfo): String {
        return ResponseDataHandler(sessionRepository.addSession(session)).getResult()
    }

    override fun markSeatIsOccupied(ticketId: Int): String {
        return ResponseDataHandler(ticketRepository.markTicketIsUsed(ticketId)).getResult()
    }
}