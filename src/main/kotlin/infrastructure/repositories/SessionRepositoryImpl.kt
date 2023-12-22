package infrastructure.repositories

import application.models.SessionInfo
import application.models.SessionWithFilmData
import application.responses.ResponseType
import core.models.Film
import core.models.SeatType
import core.models.Session
import core.repositories.SessionRepository
import infrastructure.data.DataContext
import infrastructure.data.functions.JoinFunctions
import infrastructure.data.utils.FILM
import infrastructure.data.utils.SESSION
import infrastructure.data.utils.createSessionJSON
import infrastructure.data.utils.numberPlaces
import java.time.LocalDateTime

class SessionRepositoryImpl(val dataContext: DataContext) : SessionRepository {
    override fun getAllSeatsForSession(id: Int): SessionWithFilmData? {
        var joinedData = JoinFunctions()
            .JoinGettingFullData(
                dataContext.getAllSessions(),
                dataContext.getAllFilms()
            )
        return joinedData.find { it -> it.Id == id }
    }

    override fun addSession(session: SessionInfo): ResponseType {
        var start = session.startingHour
        var end = session.startingHour.plusMinutes(session.film.durationMinutes.toLong())

        val storageSessions = dataContext.getAllSessions()
        val storageFilms = dataContext.getAllFilms()

        var joinedData = JoinFunctions().JoinSessionAndFilm(storageSessions, storageFilms)

        for (currentSession in joinedData) {
            if (isNewIntervalInterceptSmt(
                    currentSession.startingHour,
                    currentSession.startingHour.plusMinutes(currentSession.durationMinutes.toLong()),
                    start, end)) {
                return ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE
            }
        }

        val newSession = Session(storageSessions.size + 1, session.startingHour,
            MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })
        val newFilm = Film(
            storageFilms.size + 1, newSession.Id, session.film.name, session.film.description,
            session.film.rating, session.film.durationMinutes
        )
        storageSessions.add(newSession)
        storageFilms.add(newFilm)
        dataContext.saveChangesSessions(storageSessions.map { it -> createSessionJSON(it) }.toMutableList())

        return ResponseType.SESSION_ADDED
    }

    override fun changeTimeForSession(sessionId: Int, newTime: LocalDateTime): ResponseType {
        val storageSessions = dataContext.getAllSessions()
        val storageFilms = dataContext.getAllFilms()

        var joinedData = JoinFunctions().JoinSessionAndFilm(storageSessions, storageFilms)

        var element = joinedData.find { it -> it.sessionId == sessionId }

        if (element == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        var start = element.startingHour
        var end = newTime.plusMinutes(element.durationMinutes.toLong())


        for (currentSession in joinedData) {
            if (isNewIntervalInterceptSmt(
                    currentSession.startingHour,
                    currentSession.startingHour.plusMinutes(currentSession.durationMinutes.toLong()),
                    start, end
                )
            ) {
                return ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE
            }
        }

        for (item in storageSessions) {
            if (item.Id == element.sessionId) {
                item.startingHour = newTime
                dataContext.saveChangesSessions(storageSessions.map { it -> createSessionJSON(it) }.toMutableList())
                break;
            }
        }

        return ResponseType.SUCCESS
    }

    private fun isNewIntervalInterceptSmt(
        sessionStart: LocalDateTime, sessionEnd: LocalDateTime?,
        start: LocalDateTime, end: LocalDateTime?
    ): Boolean {
        return (sessionStart >= start && sessionStart <= end) ||
                (sessionEnd!! >= start && sessionEnd <= end) ||
                (sessionStart < start && sessionEnd > end);
    }
}