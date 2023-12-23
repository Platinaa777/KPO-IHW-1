package core.repositories

import application.models.SessionInfo
import application.models.SessionWithFilmData
import application.responses.ResponseType
import core.models.Film
import core.models.Session
import java.time.LocalDateTime

interface SessionRepository {
    fun getAllSeatsForSession(id: Int): SessionWithFilmData?
    fun addSession(session: SessionInfo,  storageFilms: MutableList<Film>): ResponseType
    fun changeTimeForSession(sessionId: Int, newTime: LocalDateTime, storageFilms: MutableList<Film>): ResponseType
    fun getSessionById(id: Int) : Session?
    fun getAllSessions() : MutableList<Session>
}