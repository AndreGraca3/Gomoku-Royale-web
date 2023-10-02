package pt.isel.gomoku.domain.model.variants

import pt.isel.gomoku.domain.config.PIECES_TO_WIN
import pt.isel.gomoku.domain.model.*

class FreeStyleBoard(stones: List<Stone> = emptyList(), turn: Player) : Board(stones, turn, 15) {

    override fun play(dst: Dot, player: Player): Board {
        require(isIdxInBoard(dst.row.index) && isIdxInBoard(dst.column.index)) {
            "$dst is outside of board's limits."
        }
        require(getStoneOrNull(dst) == null) { "$dst is already occupied." }

        val m = Stone(player, dst)
        val newStones = stones + m
        return when {
            checkDraw(m) -> BoardDraw(newStones, player, size)
            checkWinner(m) -> BoardWinner(newStones, player, size)
            else -> FreeStyleBoard(newStones, player.opposite())
        }
    }

    private fun checkWinner(m: Stone): Boolean {
        val dot = m.dot
        val player = m.player

        // Check horizontal
        if (countStones(dot, player, 1, 0) + countStones(dot, player, -1, 0) >= PIECES_TO_WIN)
            return true

        // Check vertical
        if (countStones(dot, player, 0, 1) + countStones(dot, player, 0, -1) >= PIECES_TO_WIN)
            return true

        // Check diagonal
        if (countStones(dot, player, 1, 1) + countStones(dot, player, -1, -1) >= PIECES_TO_WIN)
            return true

        // Check anti-diagonal
        return countStones(dot, player, 1, -1) + countStones(dot, player, -1, 1) >= PIECES_TO_WIN
    }

    private fun checkDraw(m: Stone): Boolean { // haven't played final stone yet
        return stones.count { it.player == m.player } == size * size - 1
    }
}