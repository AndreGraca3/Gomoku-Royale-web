package pt.isel.gomoku.server.data.interfaces

import pt.isel.gomoku.server.structs.dto.outbound.UserDTO
import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT
import pt.isel.gomoku.server.structs.model.User

interface IUserData {
    fun addUser(userIn: UserIn): Int
    fun getUserById(id: Int): UserOUT?
    fun changeName(id: Int, newName: String)
}