package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.game.Match

interface MatchRepository {

    fun createMatch(
        isPrivate: Boolean,
        serializedVariant: String,
        blackId: Int
    ): String

    fun getMatchesFromUser(userId: Int): List<Match>

    fun getMatchById(id: String): Match?

    fun getMatchByPreferences(
        isPrivate: Boolean,
        size: Int?,
        variant: String?
    ): Match?

    fun addToMatch(
        id: String,
        userId: Int
    ): String
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

    fun isUserInMatch(
        userId: Int
    ): Boolean
}