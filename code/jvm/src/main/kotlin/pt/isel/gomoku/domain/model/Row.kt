package pt.isel.gomoku.domain.model

import pt.isel.gomoku.domain.config.BOARD_DIM
import java.lang.IllegalArgumentException

class Row private constructor(val number: Int) {

    val index = number.toRowIdx()

    companion object {
        val values = (1..BOARD_DIM).map { Row(it) }
        operator fun invoke(idx: Int): Row {
            return values[idx]
        }
    }
}

/**
 * Transforms row's number into row
 */
fun Int.numberToRow(): Row {
    require(this in 1..BOARD_DIM)
    return Row.values[this.toRowIdx()]
}

/**
 * Transforms row's index into row
 */
fun Int.indexToRow(): Row {
    require(this in 0 until BOARD_DIM)
    return Row.values[this]
}

/**
 * Transforms row's number into index
 */
fun Int.toRowIdx(): Int {
    require(this in 1..BOARD_DIM)
    return this - 1
}