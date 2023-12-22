package application.services.interfaces

import application.models.SessionInfo
import application.models.SessionWithFilmData
import java.time.LocalDateTime

interface CinemaPartService {
    fun getAllPlacesForSession(sessionId: Int): SessionWithFilmData?
    fun editTimeOfSession(sessionId: Int, newTime: LocalDateTime): String
    fun editNameOfFilm(filmId: Int, newName: String): String
    fun editDescriptionOfFilm(filmId: Int, newDescription: String): String
    fun addNewSession(session: SessionInfo): String
}