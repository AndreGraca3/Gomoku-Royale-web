package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(
        id: String,
        isPrivate: Boolean,
        serializedVariant: String,
        serializedBoard: String,
        playerBlackId: Int,
        playerWhiteId: Int
    ): String {
        return handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("id", id)
            .bind("isPrivate", isPrivate)
            .bind("board", serializedBoard)
            .bind("variant", serializedVariant)
            .bind("player_black", playerBlackId)
            .bind("player_white", playerWhiteId)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()
    }

    override fun getMatchById(id: String): Match? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(Match::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getMatchesFromUser(userId: Int): List<Match> {
        return handle.createQuery(MatchStatements.GET_MATCHES_BY_USER_ID)
            .bind("idUser", userId)
            .mapTo(Match::class.java)
            .list()
    }

    override fun updateMatch(id: String, serializedBoard: String?, playerBlackId: Int?, playerWhiteId: Int?, winnerId: Int?) {
        handle.createUpdate(MatchStatements.UPDATE_MATCH)
            .bind("id", id)
            .bind("board", serializedBoard)
            .bind("player_black", playerBlackId)
            .bind("player_white", playerWhiteId)
            .bind("winner_id", winnerId)
            .execute()
    }
}