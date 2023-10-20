package pt.isel.gomoku.server.repository.transaction

import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.interfaces.LobbyRepository
import pt.isel.gomoku.server.repository.interfaces.UserRepository

interface Transaction {

    val userRepository: UserRepository
    val matchRepository: MatchRepository
    val lobbyRepository: LobbyRepository

    // other repository types
    fun rollback()
}