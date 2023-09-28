package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BoardTests {

    @Test
    fun `checkWinner function HORIZONTAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(0, 0)), //1
            Move(Player.WHITE, Dot(1, 1)),
            Move(Player.BLACK, Dot(0, 1)), //2
            Move(Player.WHITE, Dot(1, 2)),
            Move(Player.BLACK, Dot(0, 2)), //3
            Move(Player.WHITE, Dot(2, 1)),
            Move(Player.BLACK, Dot(0, 3)), //4
            Move(Player.WHITE, Dot(3, 4)),
        )
        val board = BoardRun(moves, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(0, 4), Player.BLACK)

        // Assert
        assertEquals(Player.BLACK, (winningBoard as BoardWinner).winner)

        // Print the board
        Board.printBoard(winningBoard.moves)
    }

    @Test
    fun `checkWinner function VERTICAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(0, 0)), //1
            Move(Player.WHITE, Dot(1, 1)),
            Move(Player.BLACK, Dot(1, 0)), //2
            Move(Player.WHITE, Dot(1, 2)),
            Move(Player.BLACK, Dot(2, 0)), //3
            Move(Player.WHITE, Dot(2, 1)),
            Move(Player.BLACK, Dot(3, 0)), //4
            Move(Player.WHITE, Dot(3, 4)),
        )
        val board = BoardRun(moves, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(4, 0), Player.BLACK)

        // Assert
        assertEquals(Player.BLACK, (winningBoard as BoardWinner).winner)

        // Print the board
        Board.printBoard(winningBoard.moves)
    }

    @Test
    fun `checkWinner function DIAGONAL`() {
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(0, 0)), //1
            Move(Player.WHITE, Dot(1, 6)),
            Move(Player.BLACK, Dot(1, 1)), //2
            Move(Player.WHITE, Dot(1, 2)),
            Move(Player.BLACK, Dot(2, 2)), //3
            Move(Player.WHITE, Dot(2, 1)),
            Move(Player.BLACK, Dot(3, 3)), //4
            Move(Player.WHITE, Dot(3, 4)),
        )
        val board = BoardRun(moves, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(4, 4), Player.BLACK)

        // Assert
        assertEquals(Player.BLACK, (winningBoard as BoardWinner).winner)

        // Print the board
        Board.printBoard(winningBoard.moves)
    }

    @Test
    fun `checkWinner function ANTIDIAGONAL`(){
        // Setup
        val moves = listOf(
            Move(Player.BLACK, Dot(0, 5)), //1
            Move(Player.WHITE, Dot(1, 6)),
            Move(Player.BLACK, Dot(1, 4)), //2
            Move(Player.WHITE, Dot(1, 2)),
            Move(Player.BLACK, Dot(2, 3)), //3
            Move(Player.WHITE, Dot(2, 1)),
            Move(Player.BLACK, Dot(3, 2)), //4
            Move(Player.WHITE, Dot(3, 4)),
        )
        val board = BoardRun(moves, Player.BLACK) // Adjust the player and game state accordingly

        // Winning Play
        val winningBoard = board.play(Dot(4, 1), Player.BLACK)

        // Assert
        assertEquals(Player.BLACK, (winningBoard as BoardWinner).winner)

        // Print the board
        Board.printBoard(winningBoard.moves)
    }

}

