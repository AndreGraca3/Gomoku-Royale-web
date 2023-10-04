package pt.isel.gomoku.server.http.model.user

data class UserCreateTokenInputModel(
    val username: String,
    val password: String
)