package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.server.http.model.stats.Rank
import pt.isel.gomoku.server.http.model.stats.UserRank
import java.sql.ResultSet
import java.sql.SQLException

class RankMapper : RowMapper<UserRank> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, ctx: StatementContext?): UserRank {
        return UserRank(
            r.getInt("id"),
            r.getString("userName"),
            Rank(
                r.getString("rankName"),
                r.getString("iconUrl")
            )
        )
    }
}