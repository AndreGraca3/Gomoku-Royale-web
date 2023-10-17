package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.Lobby
import pt.isel.gomoku.server.repository.interfaces.LobbyRepository
import pt.isel.gomoku.server.repository.jdbi.statements.LobbyStatements

class JdbiLobbyRepository(private val handle: Handle) : LobbyRepository {

    override fun createLobby(playerId: Int, isPrivate: Boolean, size: Int?, variant: String?): String {
        return handle.createUpdate(LobbyStatements.CREATE_LOBBY)
            .bind("player_id", playerId)
            .bind("isPrivate", isPrivate)
            .bind("size", size)
            .bind("variant", variant)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(String::class.java)
            .one()
    }

    override fun getLobbyById(id: String): Lobby? {
        return handle.createQuery(LobbyStatements.GET_LOBBY_BY_ID)
            .bind("id", id)
            .mapTo(Lobby::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getPublicLobbyByPreferences(size: Int?, variant: String?): Lobby? {
        return handle.createQuery(LobbyStatements.GET_PUBLIC_LOBBY_BY_PREFERENCES)
            .bind("size", size)
            .bind("variant", variant)
            .mapTo(Lobby::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getLobbiesByUser(userId: Int): List<Lobby> {
        return handle.createQuery(LobbyStatements.GET_LOBBIES_BY_USER)
            .bind("player_id", userId)
            .mapTo(Lobby::class.java)
            .list()
    }

    override fun removeLobby(id: String) {
        handle.createUpdate(LobbyStatements.REMOVE_FROM_LOBBY)
            .bind("id", id)
            .execute()
    }
}