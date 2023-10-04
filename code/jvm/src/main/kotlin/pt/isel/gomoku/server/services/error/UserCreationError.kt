package pt.isel.gomoku.server.services.error

sealed class UserCreationError {
    class UserAlreadyExists(val name: String) : UserCreationError()
    class InsecurePassword(val password: String) : UserCreationError()
}