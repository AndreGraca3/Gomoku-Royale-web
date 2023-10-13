package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.server.http.model.match.MatchCreationOut
import pt.isel.gomoku.server.http.model.match.MatchOut
import pt.isel.gomoku.server.http.model.match.MatchOutDev
import java.util.*

interface MatchRepository {

    fun createMatch(id: UUID, isPrivate: Boolean, board: String, player1_id: Int, player2_id: Int): MatchCreationOut

    fun getMatchById(id: UUID): MatchOut?

    fun getMatchDev(id : UUID) : MatchOutDev?

    fun updateMatch(id: UUID, newVisibility: String?, newWinner: Int?) : Unit

}