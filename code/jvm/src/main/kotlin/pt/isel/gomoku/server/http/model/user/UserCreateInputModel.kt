package pt.isel.gomoku.server.http.model.user

data class UserCreateInputModel(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?
)