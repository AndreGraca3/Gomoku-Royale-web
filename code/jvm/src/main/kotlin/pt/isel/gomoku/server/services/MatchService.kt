package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.interfaces.IMatchData
import pt.isel.gomoku.server.data.transactions.TransactionCtx
import pt.isel.gomoku.server.structs.dto.inbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOut

@Component
class MatchService(private val data: IMatchData, private val dataExecutor: TransactionCtx) {

    fun createMatch(matchIn: MatchIn): Int {
        return dataExecutor.execute {
            data.insertMatch(matchIn)
        }
    }

    fun getMatch(id: Int): MatchOut {
        return dataExecutor.execute {
            data.getMatchById(id)
        }
    }
}