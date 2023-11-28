package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.board.Board
import pt.isel.gomoku.domain.cell.Dot
import pt.isel.gomoku.domain.exception.GomokuGameException

data class Match(
    val id: String,
    val isPrivate: Boolean,
    val variant: Variant,
    val blackId: Int,
    val whiteId: Int?,
    val state: MatchState,
    val board: Board,
) {
    fun play(dst: Dot, player: Player): Match {
        if (state == MatchState.SETUP) throw GomokuGameException.InvalidPlay(dst) { "Match didn't start yet." }
        return copy(board = board.play(dst, player))
    }

    fun getPlayer(userId: Int) = if(blackId == userId) Player.BLACK else Player.WHITE
}

enum class MatchState {
    SETUP,
    ONGOING,
    FINISHED;

    companion object {
        fun from(state: String): MatchState = valueOf(state)
    }
}