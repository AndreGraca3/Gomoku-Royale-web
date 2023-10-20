package pt.isel.gomoku.server.service.error.match

sealed class MatchJoiningError : MatchError() {
    class MatchIsNotPrivate(val matchId: String): MatchJoiningError()
}