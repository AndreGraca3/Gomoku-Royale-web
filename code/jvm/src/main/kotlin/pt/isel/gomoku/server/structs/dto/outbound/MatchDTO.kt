package pt.isel.gomoku.server.structs.dto.outbound

import pt.isel.gomoku.domain.model.Board

class MatchIn(
    val variants: String,
    val board: Board
)

class MatchOUT(
    val variants: String,
    val board: Board
)
