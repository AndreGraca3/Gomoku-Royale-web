package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchOut
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager

@Component
class MatchService(private val trManager: TransactionManager) {

    fun createMatch(input: MatchCreateInputModel): Int {
        return trManager.run {
            it.matchRepository.createMatch(input)
        }
    }

    fun getMatch(id: Int): MatchOut {
        return trManager.run {
            it.matchRepository.getMatchById(id)
        }
    }
}