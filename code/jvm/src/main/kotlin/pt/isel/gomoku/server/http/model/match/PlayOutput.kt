package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.cell.Stone

data class PlayOutput(val stone: Stone, val matchState: String)