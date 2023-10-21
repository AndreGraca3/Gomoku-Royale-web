package pt.isel.gomoku.server.repository.transaction

import pt.isel.gomoku.server.repository.interfaces.BoardRepository
import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.interfaces.UserRepository

interface Transaction {

    val userRepository: UserRepository
    val matchRepository: MatchRepository
    val boardRepository: BoardRepository

    // other repository types
    fun rollback()
}