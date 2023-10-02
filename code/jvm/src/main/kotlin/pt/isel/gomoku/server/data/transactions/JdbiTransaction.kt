package pt.isel.gomoku.server.data.transactions

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component

@Component
class JdbiTransaction(val jdbi: Jdbi) : TransactionCtx {

    override fun <R> execute(action: (Handle) -> R): R {
        return jdbi.inTransaction<R, Exception>(action)
    }
}