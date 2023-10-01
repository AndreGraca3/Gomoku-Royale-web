package pt.isel.gomoku.server.data.interfaces

import pt.isel.gomoku.server.structs.dto.outbound.MatchIn
import pt.isel.gomoku.server.structs.dto.outbound.MatchOUT
import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

interface IMatchData {
    fun insertMatch(matchIn: MatchIn): Int
    fun getMatchById(id: Int): MatchOUT?
}