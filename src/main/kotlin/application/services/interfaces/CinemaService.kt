package application.services.interfaces

import application.models.AddedSessionInfo
import application.models.SessionInfo
import core.models.Session
import java.time.LocalDateTime

interface CinemaService {

    fun getAllPlacesForSession(sessionId: Int) : Session?
    fun buyTicketForSession(sessionId: Int, row: Int, column: Int) : String
    fun returnTicketForSession(sessionId: Int, row: Int, column: Int) : String
    fun editTimeOfSession(sessionId: Int, newTime: LocalDateTime) : String
    fun editNameOfFilm(sessionId: Int, newName: String) : String
    fun editDescriptionOfFilm(sessionId: Int, newDescription: String) : String
    fun addNewSession(session: SessionInfo) : AddedSessionInfo
    fun markSeatIsOccupied(sessionId: Int, row: Int, column: Int) : String
}