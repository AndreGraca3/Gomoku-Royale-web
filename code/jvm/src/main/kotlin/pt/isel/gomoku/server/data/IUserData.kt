package pt.isel.gomoku.server.data

import pt.isel.gomoku.server.structs.dto.outbound.UserDTO
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

interface IUserData {
    fun addUser(): UserDTO
    fun getUser(id: String): UserOUT
}