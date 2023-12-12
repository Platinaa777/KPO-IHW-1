package models

import kotlinx.serialization.Serializable

@Serializable
enum class SeatType {
    FREE,
    SOLD,
    HERE // ALREADY SITTING PERSON
}