package pt.isel.gomoku.server.structs.dto.outbound

import pt.isel.gomoku.domain.model.Board

class MatchOut(
    val variant: String,
    val board: Board
)