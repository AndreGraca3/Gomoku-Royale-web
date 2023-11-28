package pt.isel.gomoku.domain.variants

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pt.isel.gomoku.domain.Player
import pt.isel.gomoku.domain.board.FreeStyleBoard
import pt.isel.gomoku.domain.board.BoardWinner
import pt.isel.gomoku.domain.cell.Dot
import pt.isel.gomoku.domain.board.print
import pt.isel.gomoku.domain.cell.Stone

class FreeStyleBoardBoardTests {

    val size = 15

    @Test
    fun `checkWinner function HORIZONTAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.BLACK, Dot(1, 'a')), //1
            Stone(Player.WHITE, Dot(2, 'b')),
            Stone(Player.BLACK, Dot(1, 'b')), //2
            Stone(Player.WHITE, Dot(2, 'c')),
            Stone(Player.BLACK, Dot(1, 'c')), //3
            Stone(Player.WHITE, Dot(2, 'd')),
            Stone(Player.BLACK, Dot(1, 'd')), //4
            Stone(Player.WHITE, Dot(2, 'e')),
        )
        val board = FreeStyleBoard(size, stones, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(1, 'e'), Player.BLACK)
        assertTrue(winningBoard is BoardWinner)
        winningBoard.print()
    }

    @Test
    fun `checkWinner function VERTICAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.BLACK, Dot(1, 'a')), //1
            Stone(Player.WHITE, Dot(2, 'b')),
            Stone(Player.BLACK, Dot(2, 'a')), //2
            Stone(Player.WHITE, Dot(1, 'c')),
            Stone(Player.BLACK, Dot(3, 'a')), //3
            Stone(Player.WHITE, Dot(3, 'b')),
            Stone(Player.BLACK, Dot(4, 'a')), //4
            Stone(Player.WHITE, Dot(4, 'c')),
        )
        val board = FreeStyleBoard(size, stones, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function DIAGONAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.BLACK, Dot(1, 'a')), //1
            Stone(Player.WHITE, Dot(2, 'b')),
            Stone(Player.BLACK, Dot(2, 'a')), //2
            Stone(Player.WHITE, Dot(3, 'b')),
            Stone(Player.BLACK, Dot(3, 'a')), //3
            Stone(Player.WHITE, Dot(4, 'b')),
            Stone(Player.BLACK, Dot(4, 'a')), //4
            Stone(Player.WHITE, Dot(5, 'b')),
        )
        val board = FreeStyleBoard(size, stones, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function ANTI-DIAGONAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.BLACK, Dot(1, 'e')), //1
            Stone(Player.WHITE, Dot(2, 'd')),
            Stone(Player.BLACK, Dot(2, 'e')), //2
            Stone(Player.WHITE, Dot(3, 'd')),
            Stone(Player.BLACK, Dot(3, 'e')), //3
            Stone(Player.WHITE, Dot(4, 'd')),
            Stone(Player.BLACK, Dot(4, 'e')), //4
            Stone(Player.WHITE, Dot(5, 'd')),
        )
        val board = FreeStyleBoard(size, stones, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'e'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }
}