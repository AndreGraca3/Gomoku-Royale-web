package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.exception.GomokuGameException

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

    fun getPlayer(userId: Int) = if (blackId == userId) Player.BLACK else Player.WHITE

    fun getPlayerId(player: Player): Int {
        require(state == MatchState.ONGOING && whiteId != null) { "Match didn't start yet." }
        return if (player == Player.BLACK) blackId else whiteId
    }
}

enum class MatchState {
    SETUP,
    ONGOING,
    FINISHED;

    companion object {
        fun fromString(state: String): MatchState = valueOf(state)
    }

    override fun toString() = name
}