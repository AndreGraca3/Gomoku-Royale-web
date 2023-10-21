package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.board.Board

interface MatchRepository {

    fun createMatch(
        isPrivate: Boolean,
        serializedVariant: String,
        board: Board,
        blackId: Int,
        whiteId: Int?
    ): String

    fun getMatchesFromUser(userId: Int): List<Match>

    fun getMatchById(id: String): Match?

    /**
     * Updates the specified match with new values, selectively replacing non-null properties.
     */
    fun updateMatch(
        id: String,
        serializedBoard: String? = null,
        blackId: Int? = null,
        whiteId: Int? = null,
        winnerId: Int? = null
    )
}