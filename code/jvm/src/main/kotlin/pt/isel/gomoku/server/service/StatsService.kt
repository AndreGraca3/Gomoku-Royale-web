package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.http.model.UserStatsOutputModel
import pt.isel.gomoku.server.repository.dto.WinStats
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.service.errors.user.UserFetchingError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success
import kotlin.math.round

@Component
class StatsService(private val trManager: TransactionManager) {

    fun getTopRanks(skip: Int, limit: Int) = trManager.run {
        it.statsRepository.getTopRanks(skip, limit)
    }

    fun getUserStats(userId: Int): Either<UserFetchingError.UserByIdNotFound, UserStatsOutputModel> {
        return trManager.run {
            it.userRepository.getUserById(userId) ?: return@run failure(UserFetchingError.UserByIdNotFound(userId))

            val rawWinStats = it.statsRepository.getScoreStatsByUser(userId)
            val matchStats = it.statsRepository.getMatchesStatsByUser(userId)
            val totalWins = rawWinStats.winsAsBlack + rawWinStats.winsAsWhite

            success(
                UserStatsOutputModel(
                    it.statsRepository.getUserRank(userId),
                    WinStats(
                        totalWins,
                        rawWinStats.winsAsBlack,
                        rawWinStats.winsAsWhite,
                        calculateWinRate(totalWins, matchStats.totalMatches),
                        rawWinStats.draws,
                        matchStats.totalMatches - totalWins - rawWinStats.draws
                    ),
                    matchStats
                )
            )
        }
    }

    private fun calculateWinRate(wins: Int, matches: Int): Double {
        if (matches == 0) {
            return 0.toDouble()
        }

        val winRate = wins.toDouble() / matches
        // Format the double to have two decimal places
        return round(winRate * 100) / 100
    }
}