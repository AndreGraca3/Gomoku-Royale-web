package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.domain.Rank
import java.sql.ResultSet
import java.sql.SQLException

class RankMapper : ColumnMapper<Rank> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): Rank {
        return Rank(
            r.getString("rank"),
            r.getString("icon_url")
        )
    }
}