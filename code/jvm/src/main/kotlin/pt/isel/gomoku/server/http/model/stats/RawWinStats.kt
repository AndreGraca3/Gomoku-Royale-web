package pt.isel.gomoku.server.http.model.stats

data class RawWinStats(
    val totalMatches: Int,
    val winsAsBlack: Int,
    val winsAsWhite: Int,
    val draws: Int
)