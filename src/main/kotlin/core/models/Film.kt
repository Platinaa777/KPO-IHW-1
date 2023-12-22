package core.models

import kotlinx.serialization.Serializable

@Serializable
class Film(
    var id: Int,
    var sessionId: Int,
    var name: String,
    var description: String,
    var rating: Int,
    var durationMinutes: Int) {
}