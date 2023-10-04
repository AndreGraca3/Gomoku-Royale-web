package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchOut

class JdbiMatchRepository(private val handle: Handle) : MatchRepository {

    override fun createMatch(input: MatchCreateInputModel): Int {
        TODO()
    }

    override fun getMatchById(id: Int): MatchOut {
        TODO()
    }
}