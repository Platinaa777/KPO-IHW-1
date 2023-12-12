package data

import kotlinx.serialization.Serializable
import models.Film
import models.SeatType

@Serializable
class SessionJSON (
    val film: Film,
    var startingHour: String,
    var seats: MutableList<MutableList<SeatType>>
)