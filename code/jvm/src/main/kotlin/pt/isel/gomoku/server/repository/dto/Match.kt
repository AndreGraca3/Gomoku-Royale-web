package pt.isel.gomoku.server.repository.dto

import pt.isel.gomoku.domain.MatchState

data class MatchStatus(
    val id: String,
    val state: MatchState
)