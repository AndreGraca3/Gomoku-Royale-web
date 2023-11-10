package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.MatchState
import pt.isel.gomoku.server.http.model.match.MatchIdState
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.jdbi.statements.BoardStatements
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(
        blackId: Int,
        isPrivate: Boolean,
        serializedVariant: String,
        size: Int,
        type: String
    ): String {
        val matchId =  handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("isPrivate", isPrivate)
            .bind("variant", serializedVariant)
            .bind("black_id", blackId)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()

        handle.createUpdate(BoardStatements.CREATE_BOARD)
            .bind("match_id", matchId)
            .bind("size", size)
            .bind("type", type)
            .execute()

        return matchId
    }

    override fun getMatchById(id: String): Match? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(Match::class.java)
            .singleOrNull()
    }

    override fun getPublicMatchByPreferences(size: Int, variant: String): Match? {
        return handle.createQuery(MatchStatements.GET_PUBLIC_MATCH_BY_PREFERENCES)
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

    override fun getMatchStatusFromUser(userId: Int): MatchIdState? {
        return handle.createQuery(MatchStatements.MATCH_STATUS)
            .bind("userId", userId)
            .mapTo(MatchIdState::class.java)
            .firstOrNull()
    }

    override fun deleteMatch(userId: Int) {
        handle.createUpdate(MatchStatements.DELETE_MATCH)
            .bind("userId", userId)
            .execute()
    }
}