package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.game.Board

class MatchIn(
    val variants: String,
    val board: Board
)
