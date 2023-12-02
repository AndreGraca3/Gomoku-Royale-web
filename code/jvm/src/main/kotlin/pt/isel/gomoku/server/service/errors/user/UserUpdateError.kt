package pt.isel.gomoku.server.service.errors.user

sealed class UserUpdateError {
    object InvalidValues : UserUpdateError()
}