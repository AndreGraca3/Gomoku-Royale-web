package pt.isel.gomoku.server.repository.dto

import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.Rank
import pt.isel.gomoku.domain.User
import java.time.LocalDateTime

data class UserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val createdAt: LocalDateTime,
    val rank: Rank,
)

data class UserItem(
    val id: Int,
    val name: String,
    val role: String,
    val count: Int? = null,
    val rank: Rank,
)

data class AuthenticatedUser(
    val user: User,
    val token: Token,
)

data class UserAndToken(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val avatarUrl: String?,
    val rank: Rank,
    val userCreatedAt: LocalDateTime,
    val tokenValue: String,
    val tokenCreatedAt: LocalDateTime,
    val lastUsed: LocalDateTime,
) {
    val userAndToken: AuthenticatedUser
        get() = AuthenticatedUser(
            User(id, name, email, password, role, avatarUrl, rank, userCreatedAt),
            Token(tokenValue, id, tokenCreatedAt, lastUsed)
        )
}