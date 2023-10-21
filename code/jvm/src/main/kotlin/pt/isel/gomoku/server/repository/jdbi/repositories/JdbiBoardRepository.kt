package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.repository.interfaces.BoardRepository
import pt.isel.gomoku.server.repository.jdbi.statements.BoardStatements

class JdbiBoardRepository(private val handle: Handle): BoardRepository {
    override fun createBoard(matchId: String, size: Int, type: String) {
        handle.createUpdate(BoardStatements.CREATE_BOARD)
            .bind("match_id", matchId)
            .bind("size", size)
            .bind("type", type)
            .execute()
    }

    override fun updateBoard(matchId: String, stones: String, turn: Char) {
        handle.createUpdate(BoardStatements.UPDATE_BOARD)
            .bind("id", matchId)
            .bind("stones", stones)
            .bind("turn", turn)
            .execute()
    }
}