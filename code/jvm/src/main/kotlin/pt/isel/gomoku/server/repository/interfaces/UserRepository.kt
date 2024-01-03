package pt.isel.gomoku.server.repository.interfaces

import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.repository.dto.*
import java.time.LocalDateTime

interface UserRepository {

    fun createUser(name: String, email: String, password: String, avatarUrl: String?): Int

    fun getUserById(id: Int): UserInfo?

    fun getUserByName(name: String): UserInfo?

    fun getUserByEmail(email: String): User?

    fun getUsers(role: String? = null, skip: Int, limit: Int): PaginationResult<UserItem>

    fun updateUser(id: Int, name: String?, avatarUrl: String?): UserDetails

    fun deleteUser(id: Int)

    fun createToken(tokenValue: String, userId: Int): Token

    fun getUserAndTokenByTokenValue(token: String): AuthenticatedUser?

    fun getTokenByUserId(userId: Int): Token?

    fun updateTokenLastUsed(token: Token, now: LocalDateTime)

    fun deleteToken(token: String)
}