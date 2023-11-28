package pt.isel.gomoku.domain.cell

class Dot private constructor(rowNum: Int, columnSymbol: Char) {

    val row = rowNum.toRow()
    val column = columnSymbol.toColumn()

    companion object {

        private val values = mutableMapOf<Pair<Int, Char>, Dot>()

        operator fun invoke(rowNum: Int, columnSymbol: Char): Dot {
            return values.getOrPut(Pair(rowNum, columnSymbol)) { Dot(rowNum, columnSymbol) }
        }

        operator fun invoke(row: Row, column: Column): Dot {
            return values.getOrPut(Pair(row.number, column.symbol)) { Dot(row.number, column.symbol) }
        }

        operator fun invoke(rowIdx: Int, columnIdx: Int): Dot {
            return values.getOrPut(Pair(rowIdx + 1, 'a' + columnIdx)) {
                Dot(
                    rowIdx.indexToRow(),
                    columnIdx.indexToColumn()
                )
            }
        }
    }

    override fun toString(): String {
        return "${this.row.number}${this.column.symbol}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Dot)
            return this.row == other.row && this.column == other.column
        return super.equals(other)
    }
}

fun String.toDot(): Dot {
    var stringToInt = ""
    var i = 0

    while (i < this.length) {
        if (this[i].isDigit()) { // 1d or 11b
            stringToInt += this[i++]
        } else break
    }

    require(this.length >= 2 && stringToInt.isNotEmpty() && this.length == stringToInt.length + 1)
    // didn't introduce 2 values || there is no digit within those values || the 2+ values are digits.
    val row = stringToInt.toInt().toRow()
    val column = this[i].toColumn()
    return Dot(row, column)
}