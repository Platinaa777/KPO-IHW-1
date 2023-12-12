package services

import core.*
import models.Session
import repository.ICinemaRepository
import java.time.LocalDateTime

class CinemaService(var cinemaRepository: ICinemaRepository) : ICinemaService {
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