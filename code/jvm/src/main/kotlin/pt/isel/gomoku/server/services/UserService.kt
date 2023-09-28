package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.data.IUserData

@Component
class UserService(private val data: IUserData) {
    fun addUser() = data.addUser()
    fun getUser(id: String) = data.getUser(id)
}