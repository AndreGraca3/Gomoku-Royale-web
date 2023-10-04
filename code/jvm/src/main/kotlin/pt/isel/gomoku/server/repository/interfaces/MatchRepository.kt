package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.server.http.model.match.MatchIn
import pt.isel.gomoku.server.http.model.match.MatchOut

interface MatchRepository {
    fun createMatch(matchIn: MatchIn): Int
    fun getMatchById(id: Int): MatchOut
}