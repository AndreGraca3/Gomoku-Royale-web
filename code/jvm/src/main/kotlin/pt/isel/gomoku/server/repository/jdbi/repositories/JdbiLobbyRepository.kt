package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.Lobby
import pt.isel.gomoku.server.repository.interfaces.LobbyRepository
import pt.isel.gomoku.server.repository.jdbi.statements.QueueStatements

class JdbiLobbyRepository(private val handle: Handle) : LobbyRepository {

    override fun createLobby(playerId: Int, isPrivate: Boolean, size: Int?, variant: String?): String {
        return handle.createUpdate(QueueStatements.CREATE_QUEUE)
            .bind("player_id", playerId)
            .bind("isPrivate", isPrivate)
            .bind("size", size)
            .bind("variant", variant)
            .executeAndReturnGeneratedKeys("match_id")
            .mapTo(String::class.java)
            .one()
    }

    override fun getLobbyById(id: String): Lobby? {
        TODO("Not yet implemented")
    }

    override fun getLobbyByPreferences(isPrivate: Boolean, size: Int?, variant: String?): Lobby? {
        return handle.createQuery(QueueStatements.GET_QUEUE_BY_PREFERENCES)
            .bind("isPrivate", isPrivate)
            .bind("size", size)
            .bind("variant", variant)
            .mapTo(Lobby::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getLobbiesByUser(userId: Int): List<Lobby> {
        TODO("Not yet implemented")
    }

    override fun removeLobby(id: String) {
        handle.createUpdate(QueueStatements.REMOVE_FROM_QUEUE)
            .bind("match_id", id)
            .execute()
    }
}