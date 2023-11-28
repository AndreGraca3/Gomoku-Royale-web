package pt.isel.gomoku.server.service.error.match

import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.cell.Dot

sealed class MatchPlayError : MatchError() {
    class InvalidTurn(val turn: Player) : MatchPlayError()
    class InvalidPlay(val dot: Dot) : MatchPlayError()
    class NotStarted(val matchId: String) : MatchPlayError()
}