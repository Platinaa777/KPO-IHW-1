package infrastructure.repositories

import application.models.AddedSessionInfo
import application.responses.ResponseType
import application.models.SessionInfo
import infrastructure.data.DataContext
import infrastructure.data.utils.numberPlaces
import core.models.SeatType
import core.models.Session
import core.repositories.ICinemaRepository
import java.time.LocalDateTime

class CinemaRepository(private var dataContext: DataContext) : ICinemaRepository {

    override fun getAllSeatsForSession(id: Int ) : Session? {
        return dataContext.getSessionId(id)
    }

    override fun buySeatForSession(sessionId: Int, row: Int, column: Int): ResponseType {
        val session = dataContext.getSessionId(sessionId)

        var checkingResult = checkSeatIsValidToAction(session, row, column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[row][column] != SeatType.FREE) {
                return ResponseType.ALREADY_SOLD
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[row][column] = SeatType.SOLD

        dataContext.saveChangesForSession(sessionId, session)

        return ResponseType.SUCCESS
    }

    override fun returnTicketForSession(sessionId: Int, row: Int, column: Int): ResponseType {
        val session = dataContext.getSessionId(sessionId)

        var checkingResult = checkSeatIsValidToAction(session, row, column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[row][column] == SeatType.FREE) {
                return ResponseType.CANT_RETURN
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[row][column] = SeatType.FREE

        dataContext.saveChangesForSession(sessionId, session)

        return ResponseType.SUCCESS
    }

    override fun changeTimeForSession(sessionId: Int, newTime: LocalDateTime): ResponseType {
        var session = dataContext.getSessionId(sessionId)

        if (session == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        var start = newTime
        var end = newTime.plusMinutes(session.film.durationMinutes.toLong())

        val storage = dataContext.getAllSessions()

        for (i in 0..(dataContext.getSizeOfStorage() - 1)) {
            if (i == sessionId) continue

            var currentSession = storage[i]

            if (isNewIntervalInterceptSmt(currentSession.startingHour,
                    currentSession.startingHour.plusMinutes(currentSession.film.durationMinutes.toLong()),
                    start, end)) {
                return ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE
            }
        }

        session.startingHour = newTime
        dataContext.saveChangesForSession(sessionId, session)

        return ResponseType.SUCCESS
    }

    override fun changeNameOfFilm(sessionId: Int, newName: String): ResponseType {
        var session = dataContext.getSessionId(sessionId)

        if (session == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        session.film.name = newName
        dataContext.saveChangesForSession(sessionId, session)
        return ResponseType.SUCCESS
    }

    override fun changeDescriptionOfFilm(sessionId: Int, newDescription: String): ResponseType {
        var session = dataContext.getSessionId(sessionId)

        if (session == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        session.film.description = newDescription
        dataContext.saveChangesForSession(sessionId, session)
        return ResponseType.SUCCESS
    }

    override fun addSession(session: SessionInfo): AddedSessionInfo {

        var start = session.startingHour
        var end = session.startingHour.plusMinutes(session.film.durationMinutes.toLong())

        val storage = dataContext.getAllSessions()

        for (i in 0..(dataContext.getSizeOfStorage() - 1)) {
            var currentSession = storage[i]

            if (isNewIntervalInterceptSmt(currentSession.startingHour,
                    currentSession.startingHour.plusMinutes(currentSession.film.durationMinutes.toLong()),
                    start, end)) {
                return AddedSessionInfo(-1, false)
            }
        }

        val newSession = Session(session.film, session.startingHour,
            MutableList(numberPlaces) { MutableList(numberPlaces) { SeatType.FREE } })

        dataContext.insert(newSession)
        return AddedSessionInfo(dataContext.getSizeOfStorage() - 1, true)
    }

    override fun markSeatIsOccupied(sessionId: Int, row: Int, column: Int): ResponseType {
        val session = dataContext.getSessionId(sessionId)

        var checkingResult = checkSeatIsValidToAction(session, row, column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[row][column] != SeatType.SOLD) {
                return ResponseType.CANT_OCCUPY
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[row][column] = SeatType.HERE

        dataContext.saveChangesForSession(sessionId, session)

        return ResponseType.SUCCESS
    }

    private fun isNewIntervalInterceptSmt(sessionStart: LocalDateTime, sessionEnd: LocalDateTime?,
                                             start: LocalDateTime, end: LocalDateTime?): Boolean {
        return (sessionStart >= start && sessionStart <= end) ||
                (sessionEnd!! >= start && sessionEnd <= end) ||
                (sessionStart < start && sessionEnd > end);
    }

    private fun checkSeatIsValidToAction(session: Session?, row: Int, column: Int) : ResponseType {
        if (session == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        // time is wasted, so why we want to change something if it was happened ))
        if (session.startingHour.isBefore(LocalDateTime.now())) {
            return ResponseType.TIME_GONE
        }

        return ResponseType.SUCCESS
    }
}