package pt.isel.gomoku.server.service.errors.user

sealed class UserCreationError {

    class InvalidEmail(val email: String) : UserCreationError()

    class EmailAlreadyInUse(val email: String) : UserCreationError()

    class InsecurePassword(val password: String) : UserCreationError()
}