package pt.isel.gomoku.server.http.model.user

data class UserNameAndAvatar(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
)