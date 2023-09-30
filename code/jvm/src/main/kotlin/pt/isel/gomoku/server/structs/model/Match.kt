package pt.isel.gomoku.server.structs.model

import pt.isel.gomoku.domain.model.Board

data class Match(
    val id: Int,
    val variants: String,
    val board: Board
)