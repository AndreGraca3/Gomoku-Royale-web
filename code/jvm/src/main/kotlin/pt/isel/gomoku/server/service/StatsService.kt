package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.stats.UserStats
import pt.isel.gomoku.domain.stats.WinStats
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager

@Component
class StatsService(private val trManager: TransactionManager) {

    fun getTopRanks(limit: Int?) = trManager.run {
        it.statsRepository.getTopRanks(limit)
    }

    fun getUserStats(userId: Int) = trManager.run {
        val rawWinStats = it.statsRepository.getScoreStatsByUser(userId)
        val matchStats = it.statsRepository.getMatchesStatsByUser(userId)
        val totalWins = rawWinStats.winsAsBlack + rawWinStats.winsAsWhite

        UserStats(
            WinStats(
                totalWins,
                rawWinStats.winsAsBlack,
                rawWinStats.winsAsWhite,
                totalWins / rawWinStats.totalMatches.toDouble(),
                rawWinStats.draws,
                rawWinStats.totalMatches - totalWins - rawWinStats.draws
            ),
            matchStats
        )
    }
}