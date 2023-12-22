package core.repositories

import application.models.SessionInfo
import application.models.SessionWithFilmData
import application.responses.ResponseType
import java.time.LocalDateTime

interface SessionRepository {
    fun getAllSeatsForSession(id: Int): SessionWithFilmData?
    fun addSession(session: SessionInfo): ResponseType
    fun changeTimeForSession(sessionId: Int, newTime: LocalDateTime): ResponseType
}