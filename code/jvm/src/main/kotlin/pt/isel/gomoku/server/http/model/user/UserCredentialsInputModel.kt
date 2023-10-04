package pt.isel.gomoku.server.http.model.user

data class UserCredentialsInputModel(
    val email: String,
    val password: String
)