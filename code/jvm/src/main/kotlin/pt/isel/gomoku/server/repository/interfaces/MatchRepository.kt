package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.game.Match

interface MatchRepository {

    fun createMatch(
        isPrivate: Boolean,
        serializedVariant: String,
        blackId: Int,
    ): String

    fun getMatchesFromUser(userId: Int): List<Match>

    fun getMatchById(id: String): Match?

    fun getMatchByPreferences(
        size: Int?,
        variant: String?
    ): Match?

    /**
     * Updates the specified match with new values, selectively replacing non-null properties.
     */
    fun updateMatch(
        id: String,
        blackId: Int? = null,
        whiteId: Int? = null,
        state: String? = null
    ): String

    fun isUserInMatch(
        userId: Int
    ): Boolean
}