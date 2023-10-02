package pt.isel.gomoku.domain.model.variants

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pt.isel.gomoku.domain.model.BoardWinner
import pt.isel.gomoku.domain.model.Dot
import pt.isel.gomoku.domain.model.Stone
import pt.isel.gomoku.domain.model.Player

class FreeStyleBoardTests {

    @Test
    fun `checkWinner function HORIZONTAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.PLAYER_ONE, Dot(1, 'a')), //1
            Stone(Player.PLAYER_TWO, Dot(2, 'b')),
            Stone(Player.PLAYER_ONE, Dot(1, 'b')), //2
            Stone(Player.PLAYER_TWO, Dot(2, 'c')),
            Stone(Player.PLAYER_ONE, Dot(1, 'c')), //3
            Stone(Player.PLAYER_TWO, Dot(2, 'd')),
            Stone(Player.PLAYER_ONE, Dot(1, 'd')), //4
            Stone(Player.PLAYER_TWO, Dot(2, 'e')),
        )
        val board = FreeStyleBoard(stones, Player.PLAYER_ONE) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(1, 'e'), Player.PLAYER_ONE)
        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function VERTICAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.PLAYER_ONE, Dot(1, 'a')), //1
            Stone(Player.PLAYER_TWO, Dot(2, 'b')),
            Stone(Player.PLAYER_ONE, Dot(2, 'a')), //2
            Stone(Player.PLAYER_TWO, Dot(1, 'c')),
            Stone(Player.PLAYER_ONE, Dot(3, 'a')), //3
            Stone(Player.PLAYER_TWO, Dot(3, 'b')),
            Stone(Player.PLAYER_ONE, Dot(4, 'a')), //4
            Stone(Player.PLAYER_TWO, Dot(4, 'c')),
        )
        val board = FreeStyleBoard(stones, Player.PLAYER_ONE)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.PLAYER_ONE)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function DIAGONAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.PLAYER_ONE, Dot(1, 'a')), //1
            Stone(Player.PLAYER_TWO, Dot(2, 'b')),
            Stone(Player.PLAYER_ONE, Dot(2, 'a')), //2
            Stone(Player.PLAYER_TWO, Dot(3, 'b')),
            Stone(Player.PLAYER_ONE, Dot(3, 'a')), //3
            Stone(Player.PLAYER_TWO, Dot(4, 'b')),
            Stone(Player.PLAYER_ONE, Dot(4, 'a')), //4
            Stone(Player.PLAYER_TWO, Dot(5, 'b')),
        )
        val board = FreeStyleBoard(stones, Player.PLAYER_ONE)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'a'), Player.PLAYER_ONE)

        assertTrue(winningBoard is BoardWinner)
    }

    @Test
    fun `checkWinner function ANTI-DIAGONAL`() {
        // Setup
        val stones = listOf(
            Stone(Player.PLAYER_ONE, Dot(1, 'e')), //1
            Stone(Player.PLAYER_TWO, Dot(2, 'd')),
            Stone(Player.PLAYER_ONE, Dot(2, 'e')), //2
            Stone(Player.PLAYER_TWO, Dot(3, 'd')),
            Stone(Player.PLAYER_ONE, Dot(3, 'e')), //3
            Stone(Player.PLAYER_TWO, Dot(4, 'd')),
            Stone(Player.PLAYER_ONE, Dot(4, 'e')), //4
            Stone(Player.PLAYER_TWO, Dot(5, 'd')),
        )
        val board = FreeStyleBoard(stones, Player.PLAYER_ONE)

        // Winning Play
        val winningBoard = board.play(Dot(5, 'e'), Player.PLAYER_ONE)

        assertTrue(winningBoard is BoardWinner)
    }
}