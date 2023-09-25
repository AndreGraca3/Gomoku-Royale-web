package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.gomoku.domain.config.BOARD_DIM

class ColumnTests {

    @Test
    fun symbolToColumn() {
        val column = 'c'.charToColumn()
        assertNotNull(column)
        assertEquals(2, column.index)
    }

    @Test
    fun indexToColumn() {
        val column = 3.indexToColumn()
        assertEquals('d', column.symbol)
    }

    @Test
    fun invalidIndexToColumn() {
        assertThrows<IllegalArgumentException> {
            BOARD_DIM.indexToColumn()
        }
    }

    @Test
    fun invalidSymbolToColumnResultsNull() {
        assertThrows<IllegalArgumentException> {
            'x'.charToColumn()
        }
    }

    @Test
    fun allValidSymbolsToColumns() {
        assertEquals(
            List(BOARD_DIM) { it },
            ('a' until('a' + BOARD_DIM)).map { it.charToColumn().index }
        )
    }

    @Test
    fun getAllValidValuesOfColumn() {
        assertEquals(BOARD_DIM, Column.values.size)
        assertEquals(List(BOARD_DIM) { 'a' + it }, Column.values.map { it.symbol })
    }

    @Test
    fun equalsAndIdentityOfColumns() {
        val column = Column('a')
        val col1 = 'a'.charToColumn()
        assertNotNull(col1)
        val col2 = 0.indexToColumn()
        assertEquals(col1, col2)
        assertSame(col1, col2)
        assertSame(column, col1)
    }
}