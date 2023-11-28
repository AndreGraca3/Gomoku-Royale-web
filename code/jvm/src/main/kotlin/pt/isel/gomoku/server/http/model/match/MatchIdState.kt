package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.MatchState

data class MatchIdState(
    val id: String,
    val state: MatchState
)