package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.domain.game.Board
import java.sql.ResultSet
import java.sql.SQLException

class BoardMapper : ColumnMapper<Board> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): Board {
        return Board.deserialize(r.getString(columnNumber))
    }
}