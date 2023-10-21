package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.serialize
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.jdbi.statements.MatchStatements

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(
        isPrivate: Boolean,
        serializedVariant: String,
        board: Board,
        blackId: Int,
        whiteId: Int?
    ): String {

        val matchId = handle.createUpdate(MatchStatements.CREATE_MATCH)
            .bind("isPrivate", isPrivate)
            .bind("variant", serializedVariant)
            .bind("black_id", blackId)
            .bind("white_id", whiteId)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()

        handle.createUpdate(MatchStatements.CREATE_BOARD)
            .bind("match_id", matchId)
            .bind("type", board::class.simpleName)
            .bind("size", board.size)
            .bind("stones", board.stones.serialize())
            .bind("turn", board.turn.symbol)
            .execute()
        return matchId
    }

    override fun getMatchById(id: String): Match? {
        return handle.createQuery(MatchStatements.GET_MATCH_BY_ID)
            .bind("id", id)
            .mapTo(Match::class.java)
            .singleOrNull()
    }

    override fun getMatchesFromUser(userId: Int): List<Match> {
        return handle.createQuery(MatchStatements.GET_MATCHES_BY_USER_ID)
            .bind("userId", userId)
            .mapTo(Match::class.java)
            .list()
    }

    override fun updateMatch(id: String, serializedBoard: String?, blackId: Int?, whiteId: Int?, winnerId: Int?) {
        handle.createUpdate(MatchStatements.UPDATE_MATCH)
            .bind("id", id)
            .bind("board", serializedBoard)
            .bind("black_id", blackId)
            .bind("white_id", whiteId)
            .bind("winner_id", winnerId)
            .execute()
    }
}