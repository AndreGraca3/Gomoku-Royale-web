package pt.isel.gomoku.server.http.model.user

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val avatarUrl: String?,
)