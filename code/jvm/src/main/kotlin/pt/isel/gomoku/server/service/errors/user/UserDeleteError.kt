package pt.isel.gomoku.server.service.errors.user

sealed class UserDeleteError {
    object UserInAnOngoingMatch : UserDeleteError()
}
