package pt.isel.gomoku.server.structs.dto.inbound

import pt.isel.gomoku.domain.model.Board

class MatchIn(
    val variants: String,
    val board: Board
)
