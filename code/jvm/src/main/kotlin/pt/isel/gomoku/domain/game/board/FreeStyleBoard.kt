package pt.isel.gomoku.domain.game.board

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone

class FreeStyleBoard(size: Int, stones: List<Stone> = emptyList(), turn: Player = Player.BLACK) :
    Board(size, stones, turn) {

    companion object {
        const val PIECES_TO_WIN = 5
    }

    override fun applyRules(dst: Dot, player: Player): Board {
        val stone = Stone(player, dst)
        val newStones = stones + stone
        return when {
            checkDraw(stone) -> BoardDraw(size, newStones, player)
            checkWinner(stone) -> BoardWinner(size, newStones, player)
            else -> FreeStyleBoard(size, newStones, player.opposite())
        }
    }

    private fun checkWinner(m: Stone): Boolean {
        val dot = m.dot
        val player = m.player

        // Check horizontal
        if (countStones(dot, player, 1, 0) + countStones(dot, player, -1, 0) + 1 >= PIECES_TO_WIN)
            return true

        // Check vertical
        if (countStones(dot, player, 0, 1) + countStones(dot, player, 0, -1) + 1 >= PIECES_TO_WIN)
            return true

        // Check diagonal
        if (countStones(dot, player, 1, -1) + countStones(dot, player, -1, 1) + 1 >= PIECES_TO_WIN)
            return true

        // Check anti-diagonal
        return countStones(dot, player, 1, 1) + countStones(dot, player, -1, -1) + 1 >= PIECES_TO_WIN
    }

    private fun checkDraw(m: Stone): Boolean { // haven't played final stone yet
        return stones.count { it.player == m.player } == size * size - 1
    }
}
