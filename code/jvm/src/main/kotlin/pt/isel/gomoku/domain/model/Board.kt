package pt.isel.gomoku.domain.model

import pt.isel.gomoku.domain.config.MAX_DOTS

sealed class Board(val moves: List<Move>, val turnPlayer: Player) {

    fun getMove(dot: Dot, moves: List<Move> = this.moves): Move? {
        return moves.find { it.dot == dot }
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

    private fun checkWinner(m: Move): Boolean { //haven't played final move
        TODO("Pls Koff implement")
    }

    private fun checkDraw(m: Move): Boolean { //haven't played final move
        return moves.count { it.player == m.player } == MAX_DOTS
    }
}