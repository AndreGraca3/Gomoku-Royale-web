package pt.isel.gomoku.domain

import java.time.LocalDateTime

data class Lobby(
    val id: String,
    val playerId: Int,
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?,
    val createdAt: LocalDateTime
)