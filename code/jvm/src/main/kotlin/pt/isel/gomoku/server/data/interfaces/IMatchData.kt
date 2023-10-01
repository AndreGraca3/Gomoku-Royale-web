package pt.isel.gomoku.server.data.interfaces

import pt.isel.gomoku.server.structs.dto.outbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOUT

interface IMatchData {
    fun insertMatch(matchIn: MatchIn): Int
    fun getMatchById(id: Int): MatchOUT?
}