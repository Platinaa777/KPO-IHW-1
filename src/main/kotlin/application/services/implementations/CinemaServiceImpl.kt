package application.services.implementations

import application.models.AddedSessionInfo
import application.models.SessionInfo
import core.models.Session
import core.repositories.CinemaRepository
import application.services.interfaces.CinemaService
import application.services.utils.ResponseDataHandler
import java.time.LocalDateTime

class CinemaServiceImpl(var cinemaRepository: CinemaRepository) :
    CinemaService {
    override fun getAllPlacesForSession(id: Int) : Session? {
        var session =  cinemaRepository.getAllSeatsForSession(id)
        return session
    }

    override fun buyTicketForSession(sessionId: Int, row: Int, column: Int): String {
        return ResponseDataHandler(cinemaRepository.buySeatForSession(sessionId, row, column)).getResult();
    }

    override fun returnTicketForSession(sessionId: Int, row: Int, column: Int): String {
        return ResponseDataHandler(cinemaRepository.returnTicketForSession(sessionId, row, column)).getResult()
    }

    override fun editTimeOfSession(sessionId: Int, newTime: LocalDateTime) : String {
        return ResponseDataHandler(cinemaRepository.changeTimeForSession(sessionId, newTime)).getResult()
    }

    override fun editNameOfFilm(sessionId: Int, newName: String): String {
        return ResponseDataHandler(cinemaRepository.changeNameOfFilm(sessionId, newName)).getResult()
    }

    override fun editDescriptionOfFilm(sessionId: Int, newDescription: String): String {
        return ResponseDataHandler(cinemaRepository.changeDescriptionOfFilm(sessionId, newDescription)).getResult()
    }

    override fun addNewSession(session: SessionInfo): AddedSessionInfo {
        return cinemaRepository.addSession(session)
    }

    override fun markSeatIsOccupied(sessionId: Int, row: Int, column: Int): String {
        return ResponseDataHandler(cinemaRepository.markSeatIsOccupied(sessionId, row, column)).getResult()
    }
}