package infrastructure.repositories

import application.responses.BuyTicketResponse
import application.responses.ResponseType
import core.models.SeatType
import core.models.Session
import core.models.Ticket
import core.repositories.TicketRepository
import infrastructure.data.DataContext
import infrastructure.data.utils.createSessionJSON
import java.time.LocalDateTime

class TicketRepositoryImpl(private val dataContext: DataContext) : TicketRepository {

    override fun returnTicket(ticket: Ticket, sessions: MutableList<Session>): ResponseType {
        if (ticket.isDeleted) {
            return ResponseType.TICKET_IS_NOT_EXIST
        }
        val session = sessions.find { it -> it.Id == ticket.sessionId}

        val checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column)
        if (checkingResult != ResponseType.SUCCESS) {
            return checkingResult
        }

        try {
            if (session!!.seats[ticket.row][ticket.column] == SeatType.FREE &&
                session.seats[ticket.row][ticket.column] == SeatType.HERE
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
                val newTicketsGroup = deleteTicketById(ticket.id)
                dataContext.saveChangesTickets(newTicketsGroup)
                break
            }
        }

        return ResponseType.SUCCESS
    }

    override fun buyTicket(ticket: Ticket, sessions: MutableList<Session>): BuyTicketResponse {
        val newTicketsGroup = dataContext.getAllTickets()

        val session = sessions.find { it -> it.Id == ticket.sessionId }

        val checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column)
        if (checkingResult != ResponseType.SUCCESS) {
            return BuyTicketResponse(checkingResult, -1)
        }

        try {
            if (session!!.seats[ticket.row][ticket.column] != SeatType.FREE) {
                return BuyTicketResponse(ResponseType.ALREADY_SOLD, -1)
            }
        } catch (e: Exception) {
            return BuyTicketResponse(ResponseType.INVALID_ROW_COLUMN, -1)
        }

        session.seats[ticket.row][ticket.column] = SeatType.SOLD

        for (element in sessions) {
            if (element.Id == session.Id) {
                element.seats = session.seats
                dataContext.saveChangesSessions(sessions.map {it -> createSessionJSON(it) }.toMutableList())
                newTicketsGroup.add(Ticket(newTicketsGroup.size + 1, ticket.sessionId, ticket.row, ticket.column))
                dataContext.saveChangesTickets(newTicketsGroup)
                break
            }
        }

        return BuyTicketResponse(ResponseType.SUCCESS, newTicketsGroup.size)
    }

    override fun markTicketIsUsed(ticket: Ticket, session: Session, sessions: MutableList<Session>): ResponseType {
        if (ticket.isDeleted) {
            return ResponseType.TICKET_IS_NOT_EXIST
        }
        val checkingResult = checkSeatIsValidToAction(session, ticket.row, ticket.column)
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

    override fun getTicketById(id: Int): Ticket? {
        val ticket = dataContext.getAllTickets().find { it -> it.id == id }
        return ticket
    }

    override fun getAllTickets(): MutableList<Ticket> {
        return dataContext.getAllTickets()
    }

    private fun deleteTicketById(id: Int): MutableList<Ticket> {
        val tickets = dataContext.getAllTickets()

        for (ticket in tickets) {
            if (ticket.id == id) {
                ticket.isDeleted = true
            }
        }

        return tickets
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