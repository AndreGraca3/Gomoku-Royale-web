package pt.isel.gomoku.domain.game.cell

import com.fasterxml.jackson.annotation.JsonIgnore

class Column private constructor(val symbol: Char) {

    @JsonIgnore val index = symbol - 'a'

    companion object {
        private val values = mutableMapOf<Char, Column>()

        operator fun invoke(symbol: Char): Column {
            return values.getOrPut(symbol) { Column(symbol) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is Column)
            return this.symbol == other.symbol
        return super.equals(other)
    }
}

/**
 * Transforms column's symbol into column
 */
fun Char.toColumn(): Column {
    return Column(this)
}

/**
 * Transforms column's index into column
 */
fun Int.indexToColumn(): Column {
    return Column('a' + this)
}