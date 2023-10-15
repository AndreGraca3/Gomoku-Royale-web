package pt.isel.gomoku.server.http.model.queue

import java.util.UUID

data class Queue(
    val matchId: UUID,
    val playerId: Int,
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?
)