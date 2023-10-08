package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.game.Board

class MatchOutDev(
    val idMatch: String,
    val visibility: String,
    val board: Board,
    val variant: String,
    val createdAt: String,
    val player1Id: Int,
    val player2Id: Int?,
    val winnerId: Int?,
)
