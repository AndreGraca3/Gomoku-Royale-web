package pt.isel.gomoku.server.service.errors.match

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.cell.Dot

sealed class MatchPlayError : MatchError() {
    class InvalidTurn(val turn: Player) : MatchPlayError()
    class InvalidPlay(val dot: Dot) : MatchPlayError()
    object AlreadyFinished : MatchPlayError()
    class NotStarted(val matchId: String) : MatchPlayError()
}