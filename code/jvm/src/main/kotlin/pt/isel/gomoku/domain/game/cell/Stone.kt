package pt.isel.gomoku.domain.game.cell

import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.toPlayer

data class Stone(val player: Player, val dot: Dot) {

    fun serialize() = "${player.symbol}${dot.row.number}${dot.column.symbol}"

    companion object {
        /**
         * @param input Must have characters corresponding to B|W row column.
         */
        fun deserialize(input: String): Stone {
            val rowString = input.drop(1).dropLast(1)

            if (rowString.isEmpty()) throw IllegalArgumentException("The row must be a number.")

            return Stone(input[0].toPlayer(), Dot(rowString.toInt(), input[input.length - 1]))
        }
    }
}

fun List<Stone>.serialize() = joinToString(separator = "\n") { it.serialize() }