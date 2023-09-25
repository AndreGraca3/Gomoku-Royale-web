package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.gomoku.domain.config.BOARD_DIM

class DotTests {

    private val dotNumber = 1
    private val dotChar = 'c'
    private val dotRow = dotNumber.numberToRow()
    private val dotColumn = dotChar.charToColumn()

    @Test
    fun symbolToColumn() {
        val dot = "$dotNumber$dotChar".toDot()
        assertEquals(dotRow.index, dot.row.index)
        assertEquals(dotColumn.index, dot.column.index)
    }

    @Test
    fun invalidInstancesOfDot() {
        assertThrows<IllegalArgumentException> {
            Dot(BOARD_DIM.indexToRow(), BOARD_DIM.indexToColumn())
            Dot(BOARD_DIM, BOARD_DIM)
            "${BOARD_DIM}${'a' + BOARD_DIM}".toDot()
        }
    }

    @Test
    fun isEqual() {
        assertTrue(Dot(dotRow, dotColumn) == Dot(dotRow, dotColumn))
    }

    @Test
    fun isNotEqual() {
        assertFalse(Dot(dotRow, dotColumn) == Dot(dotRow, 'd'.charToColumn()))
    }

    @Test
    fun getAllValidValuesOfDot() {
        assertEquals(BOARD_DIM * BOARD_DIM, Dot.values.size)
        List(BOARD_DIM * BOARD_DIM) {
            val dot = Dot.values[it]
            assertEquals(dot.row.index, it / BOARD_DIM)
            assertEquals(dot.column.index, it % BOARD_DIM)
        }
    }
}