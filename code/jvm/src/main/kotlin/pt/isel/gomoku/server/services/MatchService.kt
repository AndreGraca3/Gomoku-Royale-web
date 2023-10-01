package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.interfaces.IMatchData
import pt.isel.gomoku.server.data.transactionManager.DataExecutor
import pt.isel.gomoku.server.structs.dto.outbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOUT

@Component
class MatchService(private val data: IMatchData, private val dataExecutor: DataExecutor) {

    fun createMatch(matchIn: MatchIn): Int {
        return dataExecutor.execute {
            data.insertMatch(matchIn)
        }
    }

    fun getMatch(id: Int): MatchOUT? {
        return dataExecutor.execute {
            data.getMatchById(id)
        }
    }
}