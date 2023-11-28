package pt.isel.gomoku.server.http.model.user

data class UserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val rank: String,
)