package pt.isel.gomoku.server.repository.dto

import pt.isel.gomoku.domain.MatchState

data class MatchStatus(
    val id: String,
    val state: MatchState
)

data class MatchItem(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
    val count: Int? = null
)