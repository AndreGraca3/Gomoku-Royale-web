package pt.isel.gomoku.server.http.model.stats

data class MatchesStats(
    val totalMatches: Int,
    val matchesAsBlack: Int,
    val matchesAsWhite: Int
)