package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.game.Match

interface MatchRepository {

    fun createMatch(
        id: String,
        isPrivate: Boolean,
        serializedVariant: String,
        serializedBoard: String,
        blackId: Int,
        whiteId: Int
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