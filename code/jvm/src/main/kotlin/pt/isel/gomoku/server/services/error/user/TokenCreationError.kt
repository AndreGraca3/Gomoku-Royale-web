package pt.isel.gomoku.server.services.error.user

sealed class TokenCreationError {
    class InvalidCredentials(val email: String, val password: String) : TokenCreationError()
}