package pt.isel.gomoku.server.services.error.match

sealed class MatchCreationError {

    class InvalidPlayerInMatch(val playerId: Int) : MatchCreationError()

    class InvalidVariant(val variant: String) : MatchCreationError()

    class AlreadyInQueue(val playerId: Int) : MatchCreationError()

}