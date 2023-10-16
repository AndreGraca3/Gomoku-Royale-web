package pt.isel.gomoku.server.service.error.match

sealed class MatchCreationError {

    class InvalidVariant(val variant: String) : MatchCreationError()

    class AlreadyInQueue(val playerId: Int) : MatchCreationError()

}