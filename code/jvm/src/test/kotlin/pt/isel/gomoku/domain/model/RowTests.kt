package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
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
        val row = dummyNum.numberToRow()
        assertEquals(dummyNum, row.number)
        assertEquals(dummyNum - 1, row.index)
    }

    @Test
    fun invalidIndexToRow() {
        assertThrows<IllegalArgumentException> {
            BOARD_DIM.indexToRow()
        }
    }

    @Test
    fun invalidNumberToRowResultsNull() {
        assertThrows<IllegalArgumentException> {
            0.numberToRow()
        }
    }

    @Test
    fun allValidNumbersToRows() {
        assertEquals(
            (0 until BOARD_DIM).toList(),
            (1..BOARD_DIM).map { it.numberToRow().index }
        )
    }

    @Test
    fun getAllValidRowValues() {
        assertEquals(BOARD_DIM, Row.values.size)
        assertEquals((1..BOARD_DIM).toList(), Row.values.map { it.number })
    }
}