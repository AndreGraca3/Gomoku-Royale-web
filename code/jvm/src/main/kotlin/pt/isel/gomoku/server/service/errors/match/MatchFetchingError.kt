package pt.isel.gomoku.server.service.errors.match

sealed class MatchFetchingError : MatchError() {

    class MatchByIdNotFound(val id: String) : MatchFetchingError()

    class UserNotInMatch(val playerId: Int, val matchId: String) : MatchFetchingError()
}