package pt.isel.gomoku.server.data.interfaces

import pt.isel.gomoku.server.structs.dto.inbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOut

interface IMatchData {
    fun insertMatch(matchIn: MatchIn): Int
    fun getMatchById(id: Int): MatchOut
}