package pt.isel.gomoku.server.data.transactions

import org.jdbi.v3.core.Handle

interface TransactionCtx {
    fun<R> execute(action: (Handle) -> R): R // can I make this generic?
}