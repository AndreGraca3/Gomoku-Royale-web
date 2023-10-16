package pt.isel.gomoku.domain.game

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.board.FreeStyleBoard
import pt.isel.gomoku.domain.game.exception.GomokuGameException
import pt.isel.gomoku.domain.game.exception.requireOrThrow

enum class Variant {

    FreeStyle {
        override val sizes = listOf(15, 19)
        override fun createBoard(size: Int?): Board {
            requireOrThrow(
                size == null || size in sizes,
                GomokuGameException.InvalidBoardSize(name, size!!, sizes)
            )
            return FreeStyleBoard(size ?: sizes.random())
        }
    };
    // Add more variants here

    companion object {
        fun from(variant: String) = values().find { it.name.equals(variant, true) }
            ?: throw GomokuGameException.InvalidVariant(variant)

        fun getRandom(): Variant = values().random()
    }

    abstract val sizes: List<Int>
    abstract fun createBoard(size: Int?): Board
}