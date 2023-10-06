package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchOut

interface MatchRepository {
    fun createMatch(input: MatchCreateInputModel): Int
    fun getMatchById(id: Int): MatchOut?
}