package pt.isel.gomoku.server.repository.dto

data class WinStats(
    val totalWins: Int,
    val winsAsBlack: Int,
    val winsAsWhite: Int,
    val winRate: Double,
    val draws: Int,
    val loses: Int,
)

data class RawWinStats(
    val totalMatches: Int,
    val winsAsBlack: Int,
    val winsAsWhite: Int,
    val draws: Int
)

data class MatchesStats(
    val totalMatches: Int,
    val matchesAsBlack: Int,
    val matchesAsWhite: Int
)