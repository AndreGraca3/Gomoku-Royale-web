package pt.isel.gomoku.domain.game

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.exception.GomokuGameException

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: Variant,
    val board: Board,
    val blackId: Int,
    val whiteId: Int,
    val winnerId: Int?,
    val state: State
) {
    fun play(dst: Dot, player: Player): Match {
        if (state == State.SETUP)
            throw GomokuGameException.NotEnoughPlayers(id)
        return copy(board = board.play(dst, player))
    }

    fun getPlayer(userId: Int) = if (blackId == userId) Player.BLACK else Player.WHITE
}

