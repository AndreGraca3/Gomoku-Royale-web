package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.server.http.model.match.MatchCreationOut
import pt.isel.gomoku.server.http.model.match.MatchOutDev
import java.util.*

interface MatchRepository {

    fun createMatch(id: UUID, isPrivate: Boolean, board: String, player1_id: Int, player2_id: Int): MatchCreationOut

    fun getMatchesFromUser(idUser: Int): List<Match>

    fun getMatchById(id: UUID): Match?

    fun getMatchDev(id : UUID) : MatchOutDev?

    fun updateMatch(id: UUID, winner: Int?)

    fun playMove(id: UUID, board: String)
}