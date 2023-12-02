package pt.isel.gomoku.server.service.errors.match

sealed class MatchJoiningError : MatchError() {
    class MatchIsNotPrivate(val matchId: String): MatchJoiningError()
    class FinishedMatch(val matchId: String): MatchJoiningError()
}