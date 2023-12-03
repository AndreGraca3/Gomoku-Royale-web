package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.http.model.UserStatsOutputModel
import pt.isel.gomoku.server.repository.dto.WinStats
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager

@Component
class StatsService(private val trManager: TransactionManager) {

    fun getTopRanks(skip: Int, limit: Int) = trManager.run {
        it.statsRepository.getTopRanks(skip, limit)
    }

    fun getUserStats(userId: Int) = trManager.run {
        // TODO: Return either with possible errors
        val rawWinStats = it.statsRepository.getScoreStatsByUser(userId)
        val matchStats = it.statsRepository.getMatchesStatsByUser(userId)
        val totalWins = rawWinStats.winsAsBlack + rawWinStats.winsAsWhite

        UserStatsOutputModel(
            it.statsRepository.getUserRank(userId),
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