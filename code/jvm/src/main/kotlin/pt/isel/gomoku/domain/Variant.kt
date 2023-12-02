package pt.isel.gomoku.domain

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.board.FreeStyleBoard
import pt.isel.gomoku.domain.game.exception.GomokuGameException
import pt.isel.gomoku.domain.game.exception.requireOrThrow

enum class Variant {

    FreeStyle {
        override val sizes = listOf(15, 19)
        override fun createBoard(size: Int?): Board {
            if (size != null)
                requireOrThrow(size in sizes, GomokuGameException.InvalidBoardSize(name, size, sizes))
            return FreeStyleBoard(size ?: sizes.random())
        }
    };
    // Add more variants here

    companion object {
        fun fromString(variant: String): Variant = values().find { it.name.equals(variant, true) }
            ?: throw GomokuGameException.InvalidVariant(variant)

        fun getRandom(): Variant = values().random()
    }

    override fun toString() = name

    abstract val sizes: List<Int>
    abstract fun createBoard(size: Int?): Board
}