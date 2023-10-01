package pt.isel.gomoku.server.data.interfaces

import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

interface IUserData {
    fun insertUser(userIn: UserIn): Int
    fun getUserById(id: Int): UserOUT?
    fun changeName(id: Int, newName: String)
    fun getUserByEmail(email: String): UserOUT
}