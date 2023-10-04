package pt.isel.gomoku.server.http.model.user

import pt.isel.gomoku.domain.User

class AuthenticatedUser(
    val user: User,
    val token: String
)