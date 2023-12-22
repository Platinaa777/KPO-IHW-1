package infrastructure.repositories

import application.responses.ResponseType
import core.models.SeatType
import core.models.Session
import core.models.Ticket
import core.repositories.TicketRepository
import infrastructure.data.DataContext
import infrastructure.data.utils.SESSION
import infrastructure.data.utils.TICKET
import infrastructure.data.utils.createSessionJSON
import java.time.LocalDateTime

class TicketRepositoryImpl(val dataContext: DataContext) : TicketRepository {
    override fun returnTicket(ticketId: Int): ResponseType {
        var newTicketsGroup = dataContext.getAllTickets()

        if (!isTicketExist(newTicketsGroup, ticketId)) {
            return ResponseType.TICKET_IS_NOT_EXIST
        }

        val ticket = newTicketsGroup.find { it -> it.id == ticketId }
        if (ticket == null) {
            return ResponseType.TICKET_IS_NOT_EXIST
        }

        val sessions = dataContext.getAllSessions()
        val session = sessions.find { it -> it.Id == ticket.sessionId}

        val checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[ticket.row][ticket.column] == SeatType.FREE &&
                session!!.seats[ticket.row][ticket.column] == SeatType.HERE
            ) {
                return ResponseType.CANT_RETURN
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[ticket.row][ticket.column] = SeatType.FREE

        for (element in sessions) {
            if (element.Id == session.Id) {
                element.seats = session.seats
                dataContext.saveChangesSessions(sessions.map { it -> createSessionJSON(it) }.toMutableList())
                newTicketsGroup = deleteTicketById(ticket.id)
                dataContext.saveChangesTickets(newTicketsGroup)
                break
            }
        }

        return ResponseType.SUCCESS
    }

    override fun buyTicket(ticket: Ticket): ResponseType {
        var newTicketsGroup = dataContext.getAllTickets()

        if (isTicketExist(newTicketsGroup, ticket.id)) {
            return ResponseType.CANT_BOUGHT_TICKET
        }

        val sessions = dataContext.getAllSessions()
        val session = sessions.find { it -> it.Id == ticket.sessionId }

        val checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[ticket.row][ticket.column] != SeatType.FREE) {
                return ResponseType.ALREADY_SOLD
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[ticket.row][ticket.column] = SeatType.SOLD

        for (element in sessions) {
            if (element.Id == session.Id) {
                element.seats = session.seats
                dataContext.saveChangesSessions(sessions.map {it -> createSessionJSON(it) }.toMutableList())
                newTicketsGroup.add(Ticket(ticket.id, ticket.sessionId, ticket.row, ticket.column))
                dataContext.saveChangesTickets(newTicketsGroup)
                break
            }
        }

        return ResponseType.SUCCESS
    }

    override fun markTicketIsUsed(ticketId: Int): ResponseType {
        val ticket = dataContext.getAllTickets().find { it -> it.id == ticketId }
        if (ticket == null) {
            return ResponseType.TICKET_IS_NOT_EXIST
        }

        val sessions = dataContext.getAllSessions()
        val session = sessions.find { it -> it.Id == ticket.sessionId }

        if (session == null) {
            return ResponseType.SESSION_NOT_EXIST
        }

        var checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column);
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session.seats[ticket.row][ticket.column] != SeatType.SOLD) {
                return ResponseType.CANT_OCCUPY
            }
        } catch (e: Exception) {
            return ResponseType.INVALID_ROW_COLUMN
        }

        session.seats[ticket.row][ticket.column] = SeatType.HERE

        for (element in sessions) {
            if (element.Id == session.Id) {
                element.seats = session.seats
                dataContext.saveChangesSessions(sessions.map { it -> createSessionJSON(it) }.toMutableList())
                break
            }
        }

        return ResponseType.SUCCESS
    }

    private fun deleteTicketById(id: Int): MutableList<Ticket> {
        var tickets = dataContext.getAllTickets()

        var newListOfTickets = mutableListOf<Ticket>()
        for (ticket in tickets) {
            if (ticket.id != id) {
                newListOfTickets.add(ticket)
            }
        }

        return newListOfTickets
    }

    private fun isTicketExist(tickets: MutableList<Ticket>, id: Int): Boolean {
        for (item in tickets) {
            if (item.id == id) {
                return true
            }
        }
        return false
    }

    private fun checkSeatIsValidToAction(session: Session?, row: Int, column: Int): ResponseType {
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