package pt.isel.gomoku.domain.game

import pt.isel.gomoku.domain.game.variants.FreeStyleBoard

abstract class Board(val stones: List<Stone>, val turn: Player, val size: Int) {

    companion object {
        fun deserialize(input: String): Board {
            val lines = input.split("\n")
            val kind = lines[0]
            val size = lines[1].toInt()
            val turn = lines[2][0].toPlayer()
            val stones = lines.drop(3).map { Stone.deserialize(it) }
            return when (kind) {
                FreeStyleBoard::class.simpleName -> FreeStyleBoard(stones, turn, size)
                BoardDraw::class.simpleName -> BoardDraw(stones, turn, size)
                BoardWinner::class.simpleName -> BoardWinner(stones, turn, size)
                else -> {
                    throw IllegalArgumentException("There is no board type for input $kind")
                }
            }
        }
    }

    fun serialize() =
        "${this::class.simpleName}\n${size}\n${turn.symbol}\n${stones.joinToString("\n") { it.serialize() }}"

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

class BoardWinner(stones: List<Stone>, val winner: Player, size: Int) : Board(stones, winner, size) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("Player $winner has already won this game.Therefore you can't play in it.")
    }
}

class BoardDraw(stones: List<Stone>, turn: Player, size: Int) : Board(stones, turn, size) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
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