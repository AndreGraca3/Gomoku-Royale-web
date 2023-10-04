package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.game.Board
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.domain.game.Player

data class Match(
    val id: String,
    val visibility: String,
    val board: Board,
    val blackPlayer: User,
    val whitePlayer: User
) {
    fun play(dst: Dot, player: Player): Match {
        return copy(board = board.play(dst, player))
    }
}