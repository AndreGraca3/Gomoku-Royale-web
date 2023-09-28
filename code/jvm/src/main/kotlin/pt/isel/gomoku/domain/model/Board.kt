package pt.isel.gomoku.domain.model

import pt.isel.gomoku.domain.config.BOARD_DIM
import pt.isel.gomoku.domain.config.MAX_DOTS

sealed class Board(val moves: List<Move>, val turnPlayer: Player) {

    fun getMove(dot: Dot, moves: List<Move> = this.moves): Move? {
        return moves.find { it.dot == dot }
    }

    // Graphic representation of the moves
    companion object {
        fun printBoard(moves: List<Move>) {
            val boardGraph = Array(BOARD_DIM) { CharArray(BOARD_DIM) { '.' } }
            for (move in moves) {
                val dot = move.dot
                val playerSymbol = if (move.player == Player.BLACK) 'B' else 'W'
                boardGraph[dot.row.index][dot.column.index] = playerSymbol
            }

            // Print the board
            for (row in boardGraph) {
                println(row.joinToString(" "))
            }
        }
    }

    abstract fun play(dst: Dot, player: Player): Board
}

class BoardWinner(moves: List<Move>, val winner: Player) : Board(moves, winner) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("Player $winner has already won this game.Therefore you can't play in it.")
    }
}

class BoardDraw(moves: List<Move>, turn: Player) : Board(moves, turn) {
    override fun play(dst: Dot, player: Player): Board {
        throw IllegalStateException("This game has already finished with a draw.")
    }
}

class BoardRun(moves: List<Move> = emptyList(), turn: Player) : Board(moves, turn) {

    override fun play(dst: Dot, player: Player): Board {
        require(dst in Dot.values) { "Not a valid Dot!" }
        require(getMove(dst) == null) { "That Dot is already occupied!" }
        val m = Move(player, dst)
        val tempMoves = moves + m
        return when {
            checkDraw(m) -> BoardDraw(tempMoves, player)
            checkWinner(m) -> BoardWinner(tempMoves, tempMoves.last().player)
            else -> BoardRun(tempMoves, player)
        }
    }

    private fun checkWinner(m: Move): Boolean {
        val dot = m.dot
        val player = m.player

        // Check horizontal
        if (countStones(dot, player, 1, 0) + countStones(dot, player, -1, 0) >= 4)
            return true

        // Check vertical
        if (countStones(dot, player, 0, 1) + countStones(dot, player, 0, -1) >= 4)
            return true

        // Check diagonal
        if (countStones(dot, player, 1, 1) + countStones(dot, player, -1, -1) >= 4)
            return true

        // Check anti-diagonal
        return countStones(dot, player, 1, -1) + countStones(dot, player, -1, 1) >= 4
    }

    private fun countStones(dot: Dot, player: Player, dx: Int, dy: Int): Int {
        var count = 0
        var currentDot = dot

        // Move in the specified direction and count consecutive stones of the same player
        repeat(4) {

            // Before creating the nextDot, check if positions are within the board
            val nextRow = currentDot.row.index + dx
            val nextColumn = currentDot.column.index + dy

            if(nextRow < 0 || nextRow > BOARD_DIM-1 || nextColumn < 0 || nextColumn > BOARD_DIM-1)
                return count
            val nextDot = Dot(nextRow, nextColumn)

            // If the next position is valid and occupied by the same player, increment the count
            if (nextDot in Dot.values && getMove(nextDot)?.player == player)
                count++
            else
                return count

            currentDot = nextDot
        }

        return count
    }




    private fun checkDraw(m: Move): Boolean { //haven't played final move
        return moves.count { it.player == m.player } == MAX_DOTS
    }

}