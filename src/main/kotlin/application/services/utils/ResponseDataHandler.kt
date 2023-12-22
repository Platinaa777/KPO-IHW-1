package application.services.utils

import application.responses.ResponseType

class ResponseDataHandler(val responseType: ResponseType) {

    fun getResult() : String{
        when(responseType) {
            ResponseType.SUCCESS -> return "SERVER: Success"
            ResponseType.CANT_RETURN -> return "SERVER: Cinema cant return ticket"
            ResponseType.SESSION_NOT_EXIST -> return "SERVER: Session does not exist"
            ResponseType.INVALID_ROW_COLUMN -> return "SERVER: Invalid row and column"
            ResponseType.TIME_GONE -> return "SERVER: Time to action (buy, return) is gone, Sorry!"
            ResponseType.ALREADY_SOLD -> return "SERVER: Seat is already sold"
            ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE -> return "SERVER: This interval intercept the old one"
            ResponseType.CANT_OCCUPY -> return "SERVER: Seat is already occupied or free"
        }
    }
}