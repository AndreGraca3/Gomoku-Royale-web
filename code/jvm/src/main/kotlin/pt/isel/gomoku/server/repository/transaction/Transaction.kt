package pt.isel.gomoku.server.repository.transaction

import pt.isel.gomoku.server.repository.interfaces.BoardRepository
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.interfaces.StatsRepository
import pt.isel.gomoku.server.repository.interfaces.UserRepository

interface Transaction {

    val userRepository: UserRepository
    val matchRepository: MatchRepository
    val boardRepository: BoardRepository
    val statsRepository: StatsRepository

    // other repository types
    fun rollback()
}