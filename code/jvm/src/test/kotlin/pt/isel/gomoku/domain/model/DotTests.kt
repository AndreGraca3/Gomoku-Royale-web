package pt.isel.gomoku.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DotTests {

    private val rowNumber = 15
    private val columnSymbol = 'c'
    private val row = rowNumber.toRow()
    private val column = columnSymbol.toColumn()

    @Test
    fun testToDot() {
        val dot = "15c".toDot()
        assertEquals(row.number, dot.row.number)
        assertEquals(column.symbol, dot.column.symbol)
    }

    @Test
    fun symbolToColumn() {
        val dot = "$rowNumber$columnSymbol".toDot()
        assertEquals(row.number, dot.row.number)
        assertEquals(column.symbol, dot.column.symbol)
    }

    @Test
    fun isEqualAndSame() {
        assertTrue(Dot(row, column) == Dot(row, column))
        assertSame(Dot(rowNumber, columnSymbol), Dot(row, column))
        assertSame(Dot(row.index, column.index), Dot(row, column))
    }

    @Test
    fun isNotEqual() {
        assertFalse(Dot(row, column) == Dot(row, 'd'.toColumn()))
    }
}