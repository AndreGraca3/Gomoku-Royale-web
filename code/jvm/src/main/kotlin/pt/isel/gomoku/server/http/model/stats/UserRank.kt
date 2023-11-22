package pt.isel.gomoku.server.http.model.stats

data class UserRank(
    val id: Int,
    val userName: String,
    val rankName: String,
    val rankImage: String
)