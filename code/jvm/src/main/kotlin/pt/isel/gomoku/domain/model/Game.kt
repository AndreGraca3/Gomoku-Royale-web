package pt.isel.gomoku.domain.model

data class Game(
    val id: String,
    val board: Board,
    val blackId: String,
    val whiteId: String,
)