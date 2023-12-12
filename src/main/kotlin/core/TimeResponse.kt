package core

import java.time.LocalDateTime
import java.time.LocalTime

class TimeResponse(var isValid: Boolean) {

    var dateTime: LocalDateTime? = null

    constructor(isValid: Boolean, dateTime: LocalDateTime) : this(isValid) {
        this.dateTime = dateTime
    }
}