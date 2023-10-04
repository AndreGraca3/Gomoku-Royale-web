package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.http.model.user.UserCreateInputModel
import pt.isel.gomoku.server.http.model.user.UserIdAndName
import pt.isel.gomoku.server.http.model.user.UserOut
import java.time.LocalDateTime

interface UserRepository {

    fun createUser(name: String, email: String, password: String, avatar: String?): Int

    fun createToken(token: Token, maxTokens: Int): Token

    fun getUserById(id: Int): UserOut?

    fun getUserByName(name: String): UserOut?

    fun getUserByEmail(email: String): UserOut?

    fun getUsers(role: String? = null): List<UserIdAndName>

    fun updateUser(input: UserCreateInputModel): UserOut

    fun deleteUser(id: Int)

    fun getUserByToken(token: String): User?

    fun getTokenByValue(value: String): Token?

    fun updateTokenLastUsed(token: Token, now: LocalDateTime)

    fun deleteToken(token: String)
}