package pt.isel.gomoku.server.service.errors.match

sealed class MatchStateError : MatchError() {
    class AlreadyStarted(val matchId: String): MatchStateError()
    class MatchIsNotPrivate(val matchId: String): MatchStateError()
    class MatchIsNotOngoing(val matchId: String): MatchStateError()
}