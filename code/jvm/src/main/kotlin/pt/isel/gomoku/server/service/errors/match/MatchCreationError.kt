package pt.isel.gomoku.server.service.errors.match

sealed class MatchCreationError : MatchError() {

    class InvalidVariant(val variant: String) : MatchCreationError()

    class InvalidBoardSize(val variant:String, val size: Int, val sizes: List<Int>) : MatchCreationError()

    class AlreadyInQueue(val playerId: Int) : MatchCreationError()

    class UserAlreadyPlaying(val playerId: Int): MatchCreationError()

    class InvalidPrivateMatch(val size: Int?, val variant: String?) : MatchCreationError()
}