package pt.isel.gomoku.domain.game

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: Variant,
    val board: Board,
    val blackUserId: Int,
    val whiteUserId: Int,
    val winnerUserId: Int?,
) {
    fun play(dst: Dot, player: Player): Match = copy(board = board.play(dst, player))

    fun getPlayer(userId: Int) = if(blackUserId == userId) Player.BLACK else Player.WHITE
}