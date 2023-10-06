package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import org.postgresql.jdbc.EscapedFunctions.NOW
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchOut
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements
import java.time.LocalDate
import java.time.LocalDateTime

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(input: MatchCreateInputModel): Int {
        return handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("visibility", input.visibility)
            .bind("board", input.board)
            .bind("variant", input.variant)
            .bind("created_at", LocalDateTime.now())
            .bind("player1_id", input.player1_id)
            .bind("player2_id", input.player2_id)
            .bind("winner_id", input.winner_id)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(Int::class.java)
            .one()
    }

    override fun getMatchById(id: Int): MatchOut? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(MatchOut::class.java)
            .findFirst()
            .orElse(null)
    }
}