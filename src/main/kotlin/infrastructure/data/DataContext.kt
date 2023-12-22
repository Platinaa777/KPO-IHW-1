package infrastructure.data

import core.models.Film
import core.models.Session
import core.models.Ticket
import infrastructure.data.entities.SessionJSON
import infrastructure.data.utils.*

class DataContext(val connectionString: String = "./data/") {

    fun getAllSessions(): MutableList<Session> {
        return readJsonSessions(connectionString + SESSION)
            .map { Mapper().toDomain(it) }.toMutableList()
    }

    fun getAllFilms(): MutableList<Film> {
        return readJsonFilms(connectionString + FILM)
    }

    fun getAllTickets(): MutableList<Ticket> {
        return readJsonTickets(connectionString + TICKET)
    }

    fun saveChangesSessions(sessions: MutableList<SessionJSON>) {
        writeToJsonSessions(sessions, connectionString + SESSION)
    }

    fun saveChangesFilms(films: MutableList<Film>) {
        writeToJsonFilms(films, connectionString + FILM)
    }

    fun saveChangesTickets(tickets: MutableList<Ticket>) {
        writeToJsonTickets(tickets, connectionString + TICKET)
    }
}

