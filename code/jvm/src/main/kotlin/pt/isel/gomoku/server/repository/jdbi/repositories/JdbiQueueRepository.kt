package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.server.http.model.queue.Queue
import pt.isel.gomoku.server.repository.interfaces.QueueRepository
import pt.isel.gomoku.server.repository.jdbi.statements.QueueStatements
import pt.isel.gomoku.server.repository.jdbi.statements.UserStatements
import java.util.*

class JdbiQueueRepository(private val handle: Handle): QueueRepository {

    override fun createQueue(playerId: Int, isPrivate: Boolean, size: Int, variant: String): String {
        return handle.createUpdate(QueueStatements.CREATE_QUEUE)
            .bind("player_id", playerId)
            .bind("isPrivate", isPrivate)
            .bind("size", size)
            .bind("variant", variant)
            .executeAndReturnGeneratedKeys("match_id")
            .mapTo(String::class.java)
            .one()
    }

    override fun getQueueByMatchId(id: String): Queue? {
        TODO("Not yet implemented")
    }

    override fun getQueueByPreferences(isPrivate: Boolean, size: Int, variant: String): Queue? {
        return handle.createQuery(QueueStatements.GET_QUEUE_BY_PREFERENCES)
            .bind("isPrivate", isPrivate)
            .bind("size", size)
            .bind("variant", variant)
            .mapTo(Queue::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun removeFromQueue(id: UUID) {
        handle.createUpdate(QueueStatements.REMOVE_FROM_QUEUE)
            .bind("match_id", id)
            .execute()
    }
}