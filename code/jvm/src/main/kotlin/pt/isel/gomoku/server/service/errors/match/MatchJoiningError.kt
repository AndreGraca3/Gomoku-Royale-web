package pt.isel.gomoku.server.service.errors.match

sealed class MatchJoiningError : MatchError() {
    class AlreadyStarted(val matchId: String): MatchJoiningError()
    class MatchIsNotPrivate(val matchId: String): MatchJoiningError()
}