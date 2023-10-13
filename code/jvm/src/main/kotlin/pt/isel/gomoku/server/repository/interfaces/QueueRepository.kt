package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.server.http.model.queue.Queue
import java.util.*

interface QueueRepository {

    fun createQueue(playerId: Int, isPrivate: Boolean, size: Int, variant: String): String

    fun getQueueByMatchId(id: String): Queue?

    fun getQueueByPreferences(isPrivate: Boolean, size: Int, variant: String): Queue?

    fun removeFromQueue(id: UUID)

}