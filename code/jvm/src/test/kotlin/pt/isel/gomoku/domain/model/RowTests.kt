package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.gomoku.domain.config.BOARD_DIM

class RowTests {

    private val dummyNum = 3

    @Test
    fun indexToRow() {
        val row = (dummyNum - 1).indexToRow()
        assertEquals(dummyNum, row.number)
    }

    @Test
    fun numberToRow() {
        val row = dummyNum.numberToRowOrNull()
        assertNotNull(row)
        assertEquals(BOARD_DIM - 3, row!!.number)
    }

    @Test
    fun invalidIndexToRow() {
        assertThrows<IndexOutOfBoundsException> {
            BOARD_DIM.indexToRow()
        }
    }

    @Test
    fun invalidNumberToRowResultsNull() {
        val row = 0.numberToRowOrNull()
        assertNull(row)
    }

    @Test
    fun allValidNumbersToRows() {
        assertEquals(
            (BOARD_DIM - 1 downTo 0).toList(),
            (1..BOARD_DIM).mapNotNull { it.numberToRowOrNull()?.index }
        )
    }

    @Test
    fun getAllValidRowValues() {
        assertEquals(BOARD_DIM, Row.values.size)
        assertEquals((BOARD_DIM downTo 1).toList(), Row.values.map { it.number })
    }

    @Test
    fun allInvalidRows() {
        val invalidNumbers = (-10..100) - (1..BOARD_DIM)
        val invalidRows = invalidNumbers.mapNotNull { it.numberToRowOrNull() }
        assertEquals(0, invalidRows.size)
    }
}