package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.http.model.match.MatchCreationOut
import pt.isel.gomoku.server.http.model.match.MatchOut
import pt.isel.gomoku.server.http.model.match.MatchOutDev
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements
import java.util.*

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(
        id: UUID,
        isPrivate: Boolean,
        board: String,
        player1_id: Int,
        player2_id: Int
    ): MatchCreationOut {
        return handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("id", id)
            .bind("isPrivate", isPrivate)
            .bind("board", board)
            //.bind("created_at", LocalDateTime.now())
            .bind("player_black", player1_id)
            .bind("player_white", player2_id)
            //.bind("winner_id", input.winner_id)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(MatchCreationOut::class.java)
            .one()
    }

    override fun getMatchById(id: UUID): MatchOut? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(MatchOut::class.java)
            .findFirst()
            .orElse(null)
    }

    // DEV OPERATION
    override fun getMatchDev(id: UUID): MatchOutDev? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(MatchOutDev::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun updateMatch(id: UUID, newVisibility: String?, newWinner: Int?) {
        handle.createUpdate(MatchStatements.UPDATE_MATCH)
            .bind("id", id)
            .bind("visibility", newVisibility)
            .bind("winner_id", newWinner)
            .execute()
    }


}