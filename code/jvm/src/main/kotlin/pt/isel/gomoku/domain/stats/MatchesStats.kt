package pt.isel.gomoku.domain.stats

data class MatchesStats(
    val totalMatches: Int,
    val matchesAsBlack: Int,
    val matchesAsWhite: Int
)