package pt.isel.gomoku.domain.model

abstract class Board(val stones: List<Stone>, val turn: Player, val size: Int) {

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

fun Board.print(stones: List<Stone>) {
    TODO("fix this after dynamic size change")
    /*val boardGraph = Array(size) { CharArray(DEFAULT_BOARD_DIM) { '.' } }
    for (move in moves) {
        val dot = move.dot
        val playerSymbol = if (move.player == Player.BLACK) 'B' else 'W'
        boardGraph[dot.row.index][dot.column.index] = playerSymbol
    }

    // Print the board
    for (row in boardGraph) {
        println(row.joinToString(" "))
    }*/
}