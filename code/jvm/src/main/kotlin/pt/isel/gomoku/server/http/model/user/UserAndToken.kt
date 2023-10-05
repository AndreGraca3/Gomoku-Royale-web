package pt.isel.gomoku.server.http.model.user

import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import java.time.LocalDateTime

data class UserAndToken(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val avatarUrl: String?,
    val tokenValue: String,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime
) {
    val userAndToken: Pair<User, Token>
        get() = Pair(
            User(id, name, email, password, role, avatarUrl),
            Token(
                tokenValue,
                id,
                createdAt,
                lastUsed
            )
        )
}