package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.repository.interfaces.BoardRepository
import pt.isel.gomoku.server.repository.jdbi.statements.BoardStatements

class JdbiBoardRepository(private val handle: Handle): BoardRepository {

    override fun createBoard(match_id: String, size: Int, type: String) {
        handle.createUpdate(BoardStatements.CREATE_BOARD)
            .bind("match_id", match_id)
            .bind("size", size)
            .bind("type", type)
            .execute()
    }
}