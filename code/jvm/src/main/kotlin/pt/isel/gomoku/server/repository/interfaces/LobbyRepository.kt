package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Lobby

interface LobbyRepository {

    fun createLobby(playerId: Int, isPrivate: Boolean, size: Int?, variant: String?): String

    fun getLobbyById(id: String): Lobby?

    fun getPublicLobbyByPreferences(size: Int?, variant: String?): Lobby?

    fun getLobbiesByUser(userId: Int): List<Lobby>

    fun removeLobby(id: String)

}