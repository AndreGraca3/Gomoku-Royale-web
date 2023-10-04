package pt.isel.gomoku.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pt.isel.gomoku.domain.game.Column
import pt.isel.gomoku.domain.game.toColumn

class ColumnTests {

    @Test
    fun symbolToColumn() {
        val column = 'c'.toColumn()
        assertNotNull(column)
        assertEquals('c', column.symbol)
    }

    @Test
    fun allValidSymbolsToColumns() {
        assertEquals(
            List(10) { 'a' + it },
            ('a' until('a' + 10)).map { it.toColumn().symbol }
        )
    }

    @Test
    fun equalsAndIdentityOfColumns() {
        val column = Column('a')
        val col1 = 'a'.toColumn()
        assertNotNull(col1)
        val col2 = Column('a')
        assertEquals(col1, col2)
        assertSame(col1, col2)
        assertSame(column, col1)
    }
}