package pt.isel.gomoku.server.http.model.user

data class UserCredentialsInput(
    val email: String,
    val password: String
)