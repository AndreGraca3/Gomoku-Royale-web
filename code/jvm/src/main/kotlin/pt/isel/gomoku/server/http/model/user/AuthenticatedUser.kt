package pt.isel.gomoku.server.http.model.user

class AuthenticatedUser(
    val user: User,
    val token: String
)