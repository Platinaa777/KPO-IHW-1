package repository

import core.*
import models.Session
import java.time.LocalDateTime

interface ICinemaRepository {
    fun getAllSeatsForSession(id: Int) : Session?
    fun buySeatForSession(sessionId: Int, row: Int, column: Int) : ResponseType
    fun returnTicketForSession(sessionId: Int, row: Int, column: Int) : ResponseType
    fun changeTimeForSession(sessionId: Int, newTime: LocalDateTime) : ResponseType
    fun changeNameOfFilm(sessionId: Int, newName: String) : ResponseType
    fun changeDescriptionOfFilm(sessionId: Int, newDescription: String) : ResponseType
    fun addSession(session: SessionInfo) : AddedSessionInfo
    fun markSeatIsOccupied(sessionId: Int, row: Int, column: Int) : ResponseType
}