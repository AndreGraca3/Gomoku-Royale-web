package pt.isel.gomoku.server.service.error.user

sealed class UserUpdateError {
    object InvalidValues : UserUpdateError()
}