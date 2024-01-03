package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Rank
import pt.isel.gomoku.server.repository.dto.MatchesStats
import pt.isel.gomoku.server.repository.dto.PaginationResult
import pt.isel.gomoku.server.repository.dto.RawWinStats
import pt.isel.gomoku.server.repository.dto.UserItem

interface StatsRepository {
    fun createStatsEntry(userId: Int)

    fun getTopRanks(skip: Int, limit: Int): PaginationResult<UserItem>

    fun getScoreStatsByUser(userId: Int): RawWinStats

    fun getMatchesStatsByUser(userId: Int): MatchesStats

    fun getUserRank(userId: Int): Rank

    fun updateWinStats(userId: Int, player: Char)

    fun updateMMR(userId: Int, mmrChange: Int): Int
}