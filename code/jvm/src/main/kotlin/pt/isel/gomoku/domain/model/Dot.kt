package pt.isel.gomoku.domain.model

import pt.isel.gomoku.domain.config.BOARD_DIM
import pt.isel.gomoku.domain.config.LAST_COORD
import pt.isel.gomoku.domain.config.MAX_DOTS
import kotlin.math.absoluteValue

class Dot private constructor(num: Int) {

    val row = (num / BOARD_DIM).indexToRow()
    val column = (num % BOARD_DIM).indexToColumn()

    companion object {
        private val values: List<Dot> = List(MAX_DOTS) { Dot(it) }

        operator fun invoke(rowIdx: Int, columnIdx: Int): Dot {
            require(rowIdx in 0 until BOARD_DIM) { " Illegal Row index must be between 0 and $LAST_COORD " }
            require(columnIdx in 0 until BOARD_DIM) { " Illegal Column index must be between 0 and $LAST_COORD " }
            val calc = rowIdx * BOARD_DIM + columnIdx
            return values[calc]
        }

        operator fun invoke(row: Row, column: Column): Dot {
            require(row in Row.values) { " Illegal Row must be between 0 and $LAST_COORD " }
            require(column in Column.values) { " Illegal Column index must be between 0 and ${LAST_COORD.toChar()} " }
            val calc = row.index * BOARD_DIM + column.index
            return values[calc]
        }
    }

    override fun toString(): String {
        return "${this.row.number}${this.column.symbol}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Dot)
            return this.row.index == other.row.index && this.column.index == other.column.index
        return super.equals(other)
    }
}

fun Dot.isNeighbour(other: Dot): Boolean {
    if (this == other) return false
    return (other.column.index - this.column.index) +
            (other.row.index - this.row.index).absoluteValue == 1
}