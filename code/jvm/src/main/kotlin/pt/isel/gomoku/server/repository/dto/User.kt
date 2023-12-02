package pt.isel.gomoku.server.repository.dto

import pt.isel.gomoku.domain.Rank
import pt.isel.gomoku.domain.User
import java.time.LocalDateTime

data class UserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val rank: Rank,
    val createdAt: LocalDateTime,
)

data class UserItem(
    val id: Int,
    val name: String,
    val role: String,
    val rank: Rank,
)

data class AuthenticatedUser(
    val user: User,
    val token: Token,
)

class Token(
    val tokenValue: String,
    val userId: Int,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime,
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
            User(id, name, email, password, role, avatarUrl, rank, tokenCreatedAt),
            Token(
                tokenValue,
                id,
                tokenCreatedAt,
                lastUsed
            )
        )
}