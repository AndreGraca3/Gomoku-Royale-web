package pt.isel.gomoku.domain.model

abstract class Board(val moves: List<Move>, val turn: Player, val size: Int) {

    fun getMove(dot: Dot, moves: List<Move> = this.moves): Move? {
        return moves.find { it.dot == dot }
    }

    fun isIdxInBoard(idx: Int): Boolean {
        return idx in 0 until size
    }

    abstract fun play(dst: Dot, player: Player): Board
}

class BoardWinner(moves: List<Move>, val winner: Player, size: Int) : Board(moves, winner, size) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("Player $winner has already won this game.Therefore you can't play in it.")
    }
}

class BoardDraw(moves: List<Move>, turn: Player, size: Int) : Board(moves, turn, size) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

fun Board.print(moves: List<Move>) {
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