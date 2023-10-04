package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.http.model.match.MatchIn
import pt.isel.gomoku.server.http.model.match.MatchOut

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(matchIn: MatchIn): Int {
        TODO()
    }

    override fun getMatchById(id: Int): MatchOut {
        TODO()
    }
}