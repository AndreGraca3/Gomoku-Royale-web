package pt.isel.gomoku.server.service.errors.user

sealed class TokenCreationError {
    class InvalidCredentials(val email: String, val password: String) : TokenCreationError()
}