package pt.isel.gomoku.server.data.transactionManager

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.springframework.context.annotation.Bean

class DataExecutor(private val jdbi: Jdbi) {
    fun <R> execute(action: (Handle) -> R): R {
        return jdbi.inTransaction<R,Exception>(action)
    }
}