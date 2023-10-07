package pt.isel.gomoku.server.services.error.match

sealed class MatchUpdateError {
    object InvalidValues : MatchUpdateError()
}
