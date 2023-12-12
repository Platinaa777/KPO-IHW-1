package services

import core.ResponseType

class ResponseDataHandler(val responseType: ResponseType) {

    fun getResult() : String{
        when(responseType) {
            ResponseType.SUCCESS -> return "Success"
            ResponseType.CANT_RETURN -> return "Cinema cant return ticket"
            ResponseType.SESSION_NOT_EXIST -> return "Session does not exist"
            ResponseType.INVALID_ROW_COLUMN -> return "Invalid row and column"
            ResponseType.TIME_GONE -> return "Time to action (buy, return) is gone, Sorry!"
            ResponseType.ALREADY_SOLD -> return "Seat is already sold"
            ResponseType.NEW_INTERVAL_INTERCEPT_OLD_ONE -> return "This interval intercept the old one"
            ResponseType.CANT_OCCUPY -> return "Seat is already occupied or free"
        }
    }
}