package pt.isel.gomoku.data

import pt.isel.gomoku.structs.dto.outbound.UserDTO

interface IUserData {
    fun addUser(): UserDTO
}