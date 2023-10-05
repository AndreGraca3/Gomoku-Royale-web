package pt.isel.gomoku.server.http.model.user

data class UserCreateInput(
    val name: String,
    val email: String,
    val password: String,
    val avatarUrl: String?
)