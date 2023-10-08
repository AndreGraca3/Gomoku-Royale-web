package pt.isel.gomoku.server.services.error.match

import java.util.*

sealed class MatchPlayError {
    object MatchNotFound : MatchPlayError()
    class MatchNotStarted(val id: UUID) : MatchPlayError()
    object MatchAlreadyFinished : MatchPlayError()
    object MatchNotVisible : MatchPlayError()
    object MatchNotPlayable : MatchPlayError()
    object MatchNotYourTurn : MatchPlayError()
    class MatchNotStartedByPlayer(val id: Int) : MatchPlayError()
}
