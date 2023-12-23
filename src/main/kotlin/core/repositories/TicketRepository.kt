package core.repositories

import application.responses.BuyTicketResponse
import application.responses.ResponseType
import core.models.Session
import core.models.Ticket

interface TicketRepository {
    fun returnTicket(ticket: Ticket, sessions: MutableList<Session>): ResponseType
    fun buyTicket(ticket: Ticket, sessions: MutableList<Session>): BuyTicketResponse
    fun markTicketIsUsed(ticket: Ticket, session: Session, sessions: MutableList<Session>): ResponseType
    fun getTicketById(id: Int) : Ticket?
    fun getAllTickets() : MutableList<Ticket>
}