package pt.isel.gomoku.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.domain.game.toColumn
import pt.isel.gomoku.domain.game.toDot
import pt.isel.gomoku.domain.game.toRow

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