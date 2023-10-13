package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.game.boards.Board

class MatchOut(
    val variant: String,
    val board: Board
)