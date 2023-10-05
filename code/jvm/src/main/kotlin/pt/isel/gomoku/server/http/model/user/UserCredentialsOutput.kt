package pt.isel.gomoku.server.http.model.user

data class UserCredentialsOutput(
    val id: Int,
    val email: String,
    val password: String
)