package pt.isel.gomoku.server.data

import pt.isel.gomoku.server.structs.dto.outbound.UserDTO

interface IUserData {
    fun addUser(): UserDTO
}