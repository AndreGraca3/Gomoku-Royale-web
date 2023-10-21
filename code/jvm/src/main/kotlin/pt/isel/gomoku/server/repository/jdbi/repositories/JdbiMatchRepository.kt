package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(
        isPrivate: Boolean,
        serializedVariant: String,
        blackId: Int
    ): String {
        return handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("isPrivate", isPrivate)
            .bind("variant", serializedVariant)
            .bind("black_id", blackId)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()
    }

    override fun getMatchById(id: String): Match? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(Match::class.java)
            .singleOrNull()
    }

    override fun getMatchByPreferences(size: Int?, variant: String?): Match? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_PREFERENCES)
            .bind("size", size)
            .bind("variant", variant)
            .mapTo(Match::class.java)
            .singleOrNull()
    }

    override fun getMatchesFromUser(userId: Int): List<Match> {
        return handle.createQuery(MatchStatements.GET_MATCHES_BY_USER_ID)
            .bind("userId", userId)
            .mapTo(Match::class.java)
            .list()
    }

    override fun updateMatch(id: String, blackId: Int?, whiteId: Int?, state: String?): String {
        return handle.createUpdate(MatchStatements.UPDATE_MATCH)
            .bind("id", id)
            .bind("black_id", blackId)
            .bind("white_id", whiteId)
            .bind("state", state)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()
    }

    override fun isUserInMatch(userId: Int): Boolean {
        return handle.createQuery(MatchStatements.IS_USER_IN_MATCH)
            .bind("userId", userId)
            .mapTo(String::class.java)
            .findFirst()
            .isPresent
    }
}