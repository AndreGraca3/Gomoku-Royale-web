package pt.isel.gomoku.domain.model.variants

import pt.isel.gomoku.domain.config.PIECES_TO_WIN
import pt.isel.gomoku.domain.model.*

class FreeStyleBoard(moves: List<Move> = emptyList(), turn: Player) : Board(moves, turn, 15) {

    override fun play(dst: Dot, player: Player): Board {
        require(isIdxInBoard(dst.row.index) && isIdxInBoard(dst.column.index)) {
            "$dst is outside of board's limits."
        }
        require(getMove(dst) == null) { "$dst is already occupied." }

        val m = Move(player, dst)
        val newMoves = moves + m
        return when {
            checkDraw(m) -> BoardDraw(newMoves, player, size)
            checkWinner(m) -> BoardWinner(newMoves, newMoves.last().player, size)
            else -> FreeStyleBoard(newMoves, player)
        }
    }

    private fun checkWinner(m: Move): Boolean {
        val dot = m.dot
        val player = m.player

        // Check horizontal
        if (countStones(dot, player, 1, 0) + countStones(dot, player, -1, 0) >= PIECES_TO_WIN - 1)
            return true

        // Check vertical
        if (countStones(dot, player, 0, 1) + countStones(dot, player, 0, -1) >= PIECES_TO_WIN - 1)
            return true

        // Check diagonal
        if (countStones(dot, player, 1, 1) + countStones(dot, player, -1, -1) >= PIECES_TO_WIN - 1)
            return true

        // Check anti-diagonal
        return countStones(dot, player, 1, -1) + countStones(dot, player, -1, 1) >= PIECES_TO_WIN - 1
    }

    private fun countStones(dot: Dot, player: Player, dx: Int, dy: Int): Int {
        var count = 0
        var currentDot = dot

        // Move in the specified direction and count consecutive stones of the same player
        repeat(PIECES_TO_WIN - 1) {

            // Before creating the nextDot, check if positions are within the board
            val nextRowIdx = currentDot.row.index + dx
            val nextColumnIdx = currentDot.column.index + dy

            if (nextRowIdx < 0 || nextRowIdx > size - 1 || nextColumnIdx < 0 || nextColumnIdx > size - 1)
                return count
            val nextDot = Dot(nextRowIdx, nextColumnIdx)

            // If the next position is valid and occupied by the same player, increment the count
            if (isIdxInBoard(nextRowIdx) && isIdxInBoard(nextColumnIdx) && getMove(nextDot)?.player == player)
                count++
            else
                return count

            currentDot = nextDot
        }
        return count
    }

    private fun checkDraw(m: Move): Boolean { //haven't played final move
        return moves.count { it.player == m.player } == size * size - 1
    }
}