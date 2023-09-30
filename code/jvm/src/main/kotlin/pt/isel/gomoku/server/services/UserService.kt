package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.interfaces.IUserData
import pt.isel.gomoku.server.data.transactionManager.DataExecutor
import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

@Component
class UserService(private val data: IUserData, private val dataExecutor: DataExecutor) {
    fun addUser(userIn: UserIn): Int {
        return dataExecutor.execute {
            if (data.getUserById(3) != null) throw Exception("User does not exist!")
            data.addUser(userIn)
        }
    }

    fun getUser(id: Int): UserOUT? = dataExecutor.execute { data.getUserById(id) }

    fun changeName(id: Int, newName: String) = dataExecutor.execute { data.changeName(id, newName) }
}