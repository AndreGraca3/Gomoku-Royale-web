package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.interfaces.IUserData
import pt.isel.gomoku.server.data.transactions.TransactionCtx
import pt.isel.gomoku.server.structs.dto.inbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

@Component
class UserService(private val data: IUserData, private val dataExecutor: TransactionCtx) {
    fun createUser(userIn: UserIn): Int {
        return dataExecutor.execute {
            data.getUserByEmail(userIn.email) // email probably is unique.
            data.insertUser(userIn)
        }
    }

    fun getUser(id: Int): UserOUT? = dataExecutor.execute { data.getUserById(id) }

    fun changeName(id: Int, newName: String) = dataExecutor.execute { data.changeName(id, newName) }
}