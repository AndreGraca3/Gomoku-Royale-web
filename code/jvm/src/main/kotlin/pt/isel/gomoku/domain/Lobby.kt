package pt.isel.gomoku.domain

data class Lobby(
    val id: String,
    val playerId: Int,
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?
)