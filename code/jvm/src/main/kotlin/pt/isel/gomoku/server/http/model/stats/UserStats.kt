package pt.isel.gomoku.server.http.model.stats

data class UserStats(
    val id: Int,
    val userName: String,
    val rankName: String,
    val rankImage: String
)