package pt.isel.gomoku.domain.game

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: Variant,
    val board: Board,
    val blackId: Int,
    val whiteId: Int,
    val winnerId: Int?,
) {
    fun play(dst: Dot, player: Player): Match = copy(board = board.play(dst, player))

    fun getPlayer(userId: Int) = if(blackId == userId) Player.BLACK else Player.WHITE
}