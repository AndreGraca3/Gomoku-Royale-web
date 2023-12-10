package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.exception.GomokuGameException
import pt.isel.gomoku.domain.game.exception.requireOrThrow
import kotlin.reflect.full.primaryConstructor

sealed class Board(val size: Int, val stones: List<Stone>, val turn: Player) {

    companion object {
        fun create(type: String, size: Int, serializedStones: String, turn: Player): Board {
            val stones = if (serializedStones.isEmpty()) emptyList() else serializedStones.split("\n")
                .map { Stone.deserialize(it) }
            return Board::class.sealedSubclasses.find { it.simpleName == type }
                ?.primaryConstructor?.call(size, stones, turn)
                ?: throw IllegalArgumentException("There is no board type for input $type")
        }
    }

    fun getStoneOrNull(dot: Dot, stones: List<Stone> = this.stones): Stone? {
        return stones.find { it.dot == dot }
    }

    private fun isIdxInBoard(idx: Int): Boolean {
        return idx in 0 until size
    }

    /**
     * Counts the stones of a player in a given direction (including current dot)
     */
    fun countStones(dot: Dot, player: Player, dx: Int, dy: Int): Int {
        var currentDot = dot
        var count = 1

        while (isIdxInBoard(currentDot.row.index) && isIdxInBoard(currentDot.column.index)) {
            val nextRowIdx = currentDot.row.index + dy
            val nextColumnIdx = currentDot.column.index + dx

            currentDot = Dot(nextRowIdx, nextColumnIdx)
            val stone = getStoneOrNull(currentDot)

            if (stone == null || stone.player != player) break
            count++
        }
        return count
    }

    fun play(dst: Dot, player: Player): Board {
        requireOrThrow(turn == player, GomokuGameException.InvalidTurn(turn) {
            "It's not $player's turn."
        })
        requireOrThrow(isIdxInBoard(dst.row.index) && isIdxInBoard(dst.column.index),
            GomokuGameException.InvalidPlay(dst) { "$dst is outside of board's limits." }
        )
        requireOrThrow(getStoneOrNull(dst) == null, GomokuGameException.InvalidPlay(dst) {
            "$dst is already occupied."
        })
        return applyRules(dst, player)
    }

    protected abstract fun applyRules(dst: Dot, player: Player): Board
}

class BoardWinner(size: Int, stones: List<Stone>, val winner: Player) : Board(size, stones, winner) {
    override fun applyRules(dst: Dot, player: Player): Board {
        throw GomokuGameException.AlreadyFinished() { "Player $winner has already won this game." }
    }
}

class BoardDraw(size: Int, stones: List<Stone>, turn: Player) : Board(size, stones, turn) {
    override fun applyRules(dst: Dot, player: Player): Board {
        throw GomokuGameException.AlreadyFinished() { "This game has already finished with a draw." }
    }
}

fun Board.print() {
    val boardGraph = Array(size) { CharArray(size) { '.' } }
    for (stone in stones) {
        val dot = stone.dot
        val playerSymbol = if (stone.player == Player.BLACK) 'B' else 'W'
        boardGraph[size - dot.row.index - 1][dot.column.index] = playerSymbol
    }

    // Print the board
    for (row in boardGraph) {
        println(row.joinToString(" "))
    }
}