package pt.isel.gomoku.server.repository.transaction.managers

import pt.isel.gomoku.server.repository.transaction.Transaction

interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}