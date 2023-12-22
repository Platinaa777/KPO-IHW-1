package infrastructure.data.entities

import core.models.SeatType
import kotlinx.serialization.Serializable

@Serializable
class SessionJSON(
    val Id: Int,
    var startingHour: String,
    var seats: MutableList<MutableList<SeatType>>
)