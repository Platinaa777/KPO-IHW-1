package application.responses

import java.time.LocalDateTime

class TimeResponse(var isValid: Boolean) {
    var dateTime: LocalDateTime? = null

    constructor(isValid: Boolean, dateTime: LocalDateTime) : this(isValid) {
        this.dateTime = dateTime
    }
}