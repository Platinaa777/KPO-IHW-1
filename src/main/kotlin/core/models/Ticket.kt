package core.models

import kotlinx.serialization.Serializable

@Serializable
class Ticket(var id: Int, val sessionId: Int, val row: Int, val column: Int)