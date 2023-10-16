package pt.isel.gomoku.server.service.error.match

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.cell.Dot

sealed class MatchPlayError {
    object AlreadyFinished : MatchPlayError()
    class InvalidTurn(turn: Player) : MatchPlayError()
    class InvalidPlay(dot: Dot, message: String) : MatchPlayError()
}