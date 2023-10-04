package pt.isel.gomoku.server.services.error.user

sealed class UserCreationError {
    class EmailAlreadyInUse(val email: String) : UserCreationError()

    class InsecurePassword(val password: String) : UserCreationError()
}