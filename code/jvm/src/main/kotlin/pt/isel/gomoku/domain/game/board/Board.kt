package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.exception.GomokuGameException
import pt.isel.gomoku.domain.game.toPlayer
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

    // TODO: Remove this, we won't need this method when we have a board as an entity in the database
    fun serialize() =
        "${this::class.simpleName}\n${size}\n${turn.symbol}${
            stones.joinToString(separator = "\n", prefix = "\n") { it.serialize() }.takeIf { stones.isNotEmpty() }
                .orEmpty()
        }"

    fun getStoneOrNull(dot: Dot, stones: List<Stone> = this.stones): Stone? {
        return stones.find { it.dot == dot }
    }

    fun isIdxInBoard(idx: Int): Boolean {
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

    abstract fun play(dst: Dot, player: Player): Board
}

class BoardWinner(size: Int, stones: List<Stone>, val winner: Player) : Board(size, stones, winner) {
    override fun play(dst: Dot, player: Player): Board {
        throw GomokuGameException.InvalidPlay(dst) { "Player $winner has already won this game." }
    }
}

class BoardDraw(size: Int, stones: List<Stone>, turn: Player) : Board(size, stones, turn) {
    override fun play(dst: Dot, player: Player): Board {
        throw GomokuGameException.InvalidPlay(dst) { "This game has already finished with a draw." }
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