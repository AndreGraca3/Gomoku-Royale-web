package pt.isel.gomoku.server.services.error.user

sealed class UserUpdateError {
    object InvalidValues : UserUpdateError()
}