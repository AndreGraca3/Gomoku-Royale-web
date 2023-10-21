package pt.isel.gomoku.server.repository.transaction

import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.interfaces.LobbyRepository
import pt.isel.gomoku.server.repository.interfaces.StatsRepository
import pt.isel.gomoku.server.repository.interfaces.UserRepository

interface Transaction {

    val userRepository: UserRepository
    val matchRepository: MatchRepository
    val lobbyRepository: LobbyRepository
    val statsRepository: StatsRepository

    // other repository types
    fun rollback()
}