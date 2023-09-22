package pt.isel.gomoku.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.data.IUserData

@Component
class UserService(private val data: IUserData) {
    fun addUser() = data.addUser()
}