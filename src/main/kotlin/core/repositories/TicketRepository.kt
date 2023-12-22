package core.repositories

import application.responses.ResponseType
import core.models.Ticket

interface TicketRepository {
    fun returnTicket(ticketId: Int): ResponseType
    fun buyTicket(ticket: Ticket): ResponseType
    fun markTicketIsUsed(ticketId: Int): ResponseType
}