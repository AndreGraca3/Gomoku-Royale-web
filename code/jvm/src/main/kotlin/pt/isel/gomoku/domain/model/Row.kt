package pt.isel.gomoku.domain.model

class Row private constructor(val number: Int) {

    val index = number - 1

    companion object {
        private val values = mutableMapOf<Int, Row>()

        operator fun invoke(number: Int): Row {
            return values.getOrPut(number) { Row(number) }
        }

    }

    override fun equals(other: Any?): Boolean {
        if (other is Row)
            return this.number == other.number
        return super.equals(other)
    }
}

/**
 * Transforms row's number into row
 */
fun Int.toRow(): Row {
    return Row(this)
}

/**
 * Transforms row's index into row
 */
fun Int.indexToRow(): Row {
    return Row(this + 1)
}