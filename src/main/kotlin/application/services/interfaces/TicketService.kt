package application.services.interfaces

import core.models.Ticket

interface TicketService {
    fun buyTicketForSession(ticket: Ticket): String
    fun returnTicketForSession(ticketId: Int): String
    fun markSeatIsOccupied(ticketId: Int): String
}