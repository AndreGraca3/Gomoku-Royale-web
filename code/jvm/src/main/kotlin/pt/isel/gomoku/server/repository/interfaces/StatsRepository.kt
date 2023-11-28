package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.server.http.model.stats.MatchesStats
import pt.isel.gomoku.server.http.model.stats.Rank
import pt.isel.gomoku.server.http.model.stats.RawWinStats
import pt.isel.gomoku.server.http.model.stats.UserRank

interface StatsRepository {
    fun getTopRanks(limit: Int?): List<UserRank>

    fun getScoreStatsByUser(userId: Int): RawWinStats

    fun getMatchesStatsByUser(userId: Int): MatchesStats

    fun getUserRank(userId: Int): Rank
}