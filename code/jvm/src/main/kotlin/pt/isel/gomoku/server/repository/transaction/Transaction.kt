package pt.isel.gomoku.server.repository.transaction

import pt.isel.gomoku.server.repository.interfaces.MatchRepository
import pt.isel.gomoku.server.repository.interfaces.UserRepository

interface Transaction {

    val userRepository: UserRepository
    val matchRepository: MatchRepository

    // other repository types
    fun rollback()
}