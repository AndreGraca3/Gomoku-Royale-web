package pt.isel.gomoku.domain.model.variants

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pt.isel.gomoku.domain.model.BoardWinner
import pt.isel.gomoku.domain.model.Dot
import pt.isel.gomoku.domain.model.Move
import pt.isel.gomoku.domain.model.Player

class FreeStyleBoardTests {

    @Test
    fun `checkWinner function HORIZONTAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(1, 'a')), //1
            Move(Player.WHITE, Dot(2, 'b')),
            Move(Player.BLACK, Dot(1, 'b')), //2
            Move(Player.WHITE, Dot(2, 'c')),
            Move(Player.BLACK, Dot(1, 'c')), //3
            Move(Player.WHITE, Dot(2, 'd')),
            Move(Player.BLACK, Dot(1, 'd')), //4
            Move(Player.WHITE, Dot(2, 'e')),
        )
        val board = FreeStyleBoard(moves, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(1, 'e'), Player.BLACK)
        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function VERTICAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(1, 'a')), //1
            Move(Player.WHITE, Dot(2, 'b')),
            Move(Player.BLACK, Dot(2, 'a')), //2
            Move(Player.WHITE, Dot(1, 'c')),
            Move(Player.BLACK, Dot(3, 'a')), //3
            Move(Player.WHITE, Dot(3, 'b')),
            Move(Player.BLACK, Dot(4, 'a')), //4
            Move(Player.WHITE, Dot(4, 'c')),
        )
        val board = FreeStyleBoard(moves, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function DIAGONAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(1, 'a')), //1
            Move(Player.WHITE, Dot(2, 'b')),
            Move(Player.BLACK, Dot(2, 'a')), //2
            Move(Player.WHITE, Dot(3, 'b')),
            Move(Player.BLACK, Dot(3, 'a')), //3
            Move(Player.WHITE, Dot(4, 'b')),
            Move(Player.BLACK, Dot(4, 'a')), //4
            Move(Player.WHITE, Dot(5, 'b')),
        )
        val board = FreeStyleBoard(moves, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function ANTI-DIAGONAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(1, 'e')), //1
            Move(Player.WHITE, Dot(2, 'd')),
            Move(Player.BLACK, Dot(2, 'e')), //2
            Move(Player.WHITE, Dot(3, 'd')),
            Move(Player.BLACK, Dot(3, 'e')), //3
            Move(Player.WHITE, Dot(4, 'd')),
            Move(Player.BLACK, Dot(4, 'e')), //4
            Move(Player.WHITE, Dot(5, 'd')),
        )
        val board = FreeStyleBoard(moves, Player.BLACK)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'e'), Player.BLACK)

        assertTrue(winningBoard is BoardWinner)
    }
}