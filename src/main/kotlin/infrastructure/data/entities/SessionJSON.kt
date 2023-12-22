package infrastructure.data.entities

import kotlinx.serialization.Serializable
import core.models.Film
import core.models.SeatType

@Serializable
class SessionJSON (
    val film: Film,
    var startingHour: String,
    var seats: MutableList<MutableList<SeatType>>
)