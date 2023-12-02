package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.domain.MatchState
import java.sql.ResultSet
import java.sql.SQLException

class MatchStateMapper : ColumnMapper<MatchState> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): MatchState {
        return MatchState.fromString(r.getString(columnNumber))
    }
}