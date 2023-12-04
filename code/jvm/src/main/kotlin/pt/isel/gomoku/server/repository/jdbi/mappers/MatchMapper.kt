package pt.isel.gomoku.server.repository.jdbi.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.domain.MatchState
import pt.isel.gomoku.domain.Variant
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.toPlayer
import java.sql.ResultSet
import java.sql.SQLException

class MatchMapper : RowMapper<Match> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, ctx: StatementContext?): Match {
        val board = Board.create(
            r.getString("type"),
            r.getInt("size"),
            r.getString("stones"),
            r.getString("turn")[0].toPlayer()
        )

        return Match(
            r.getString("id"),
            r.getBoolean("isPrivate"),
            Variant.fromString(r.getString("variant")),
            r.getInt("black_id"),
            if(r.getInt("white_id") == 0) null else r.getInt("white_id"),
            MatchState.fromString(r.getString("state")),
            board
        )
    }
}