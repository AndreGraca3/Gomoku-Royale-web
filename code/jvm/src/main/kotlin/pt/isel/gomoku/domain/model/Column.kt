package pt.isel.gomoku.domain.model

import pt.isel.gomoku.domain.config.A_CHAR_CODE
import pt.isel.gomoku.domain.config.BOARD_DIM

class Column private constructor(var symbol: Char) {

    var index = symbol.toColumnIdx()

    companion object {
        val values = (0 until BOARD_DIM).map { Column((it + A_CHAR_CODE).toChar()) }
        operator fun invoke(symbol: Char): Column {
            return values[symbol.toColumnIdx()]
        }
    }
}

/**
 * Transforms column's symbol into row
 */
fun Char.toColumnOrNull(): Column? {
    val colIdx = this.toColumnIdx()
    require(colIdx != -1) { return null }
    return Column.values[colIdx]
}

/**
 * Transforms column's index into row
 */
fun Int.indexToColumn(): Column {
    require(this in 0 until BOARD_DIM) { throw IndexOutOfBoundsException("This index is out of bounds!") }
    return Column.values[this]
}

/**
 * Transforms column's symbol into index
 */
fun Char.toColumnIdx(): Int {
    val result = this.code - A_CHAR_CODE
    if (this.code !in 'a'.code..('a' + BOARD_DIM - 1).code) return -1
    return result
}