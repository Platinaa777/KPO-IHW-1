package core.models

import kotlinx.serialization.Serializable

@Serializable
class Film(var name: String,
           var description: String,
           var rating: Int,
           var durationMinutes: Int)  {
}