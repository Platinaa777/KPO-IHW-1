package application.services.handlers

import application.responses.ResponseType

class ResponseDataHandler(val responseType: ResponseType) {

    fun getResult(): String {
        return when (responseType) {
            ResponseType.SUCCESS -> "SERVER: Success"
            ResponseType.CANT_RETURN -> "SERVER: Cinema cant return ticket"
            ResponseType.SESSION_NOT_EXIST -> "SERVER: Session does not exist"
            ResponseType.INVALID_ROW_COLUMN -> "SERVER: Invalid row and column"
            ResponseType.TIME_GONE -> "SERVER: Time to action (buy, return) is gone, Sorry!"
            ResponseType.ALREADY_SOLD -> "SERVER: Seat is already sold"
            ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE -> "SERVER: This interval intercept the old one"
            ResponseType.CANT_OCCUPY -> "SERVER: Seat is already occupied or free"
            ResponseType.SESSION_ADDED -> "SERVER: Session successfully was added"
            ResponseType.TICKET_IS_NOT_EXIST -> "SERVER: Ticket is not exist"
            ResponseType.CANT_BOUGHT_TICKET -> "SERVER: Ticket is already bought, you cant buy it again"
            ResponseType.FILM_IS_NOT_EXIST -> "SERVER: "
        }
    }
}