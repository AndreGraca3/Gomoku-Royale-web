package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.game.boards.Board
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.domain.game.Player
import java.util.UUID

data class Match(
    val id: UUID,
    val isPrivate: Boolean,
    val board: Board,
    val player_black: Int,
    val player_white: Int
) {
    fun play(dst: Dot, player: Player): Match {
        return copy(board = board.play(dst, player))
    }

    fun getPlayer(userId: Int): Player {
        return if(player_black == userId) Player.BLACK else Player.WHITE
    }
}