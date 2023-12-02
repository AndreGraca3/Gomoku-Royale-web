package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.server.repository.dto.MatchStatus

interface MatchRepository {

    fun createMatch(
        blackId: Int,
        isPrivate: Boolean,
        serializedVariant: String,
        size: Int,
        type: String
    ): String

    fun getMatchesFromUser(userId: Int): List<Match>

    fun getMatchById(id: String): Match?

    fun getPublicMatchByPreferences(
        size: Int,
        variant: String
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

    /**
     * Returns the match state of the specified user. If the user is not in a match, returns null.
     */
    fun getMatchStatusFromUser(userId: Int): MatchStatus?

    fun deleteMatch(
        userId: Int
    )
}