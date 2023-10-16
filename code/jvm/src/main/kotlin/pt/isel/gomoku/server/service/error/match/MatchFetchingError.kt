package pt.isel.gomoku.server.service.error.match

sealed class MatchFetchingError {

    class MatchByIdNotFound(val id : String) : MatchFetchingError()

    class UserNotInMatch(val playerId: Int, val matchId: String): MatchFetchingError()
}