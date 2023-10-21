package pt.isel.gomoku.domain.stats

data class WinStats(
    val totalWins: Int,
    val winsAsBlack: Int,
    val winsAsWhite: Int,
    val winRate: Double,
    val draws: Int,
    val loses: Int,
)