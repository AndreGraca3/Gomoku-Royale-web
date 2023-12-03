package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.repository.dto.*
import pt.isel.gomoku.server.repository.interfaces.UserRepository
import pt.isel.gomoku.server.repository.jdbi.statements.UserStatements
import java.time.LocalDateTime

class JdbiUserRepository(private val handle: Handle) : UserRepository {

    override fun createUser(name: String, email: String, password: String, avatarUrl: String?): Int {
        return handle.createUpdate(UserStatements.CREATE_USER)
            .bind("name", name)
            .bind("email", email)
            .bind("password", password)
            .bind("avatar_url", avatarUrl)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(Int::class.java)
            .one()
    }

    override fun getUserById(id: Int): UserInfo? {
        return handle.createQuery(UserStatements.GET_USER_BY_ID)
            .bind("id", id)
            .mapTo(UserInfo::class.java)
            .singleOrNull()
    }

    override fun getUserByName(name: String): UserInfo? {
        return handle.createQuery(UserStatements.GET_USER_BY_NAME)
            .bind("name", name)
            .mapTo(UserInfo::class.java)
            .singleOrNull()
    }

    override fun getUserByEmail(email: String): User? {
        return handle.createQuery(UserStatements.GET_USER_BY_EMAIL)
            .bind("email", email)
            .mapTo(User::class.java)
            .singleOrNull()
    }

    override fun getUsers(role: String?, skip: Int, limit: Int): PaginationResult<UserItem> {
        val users = handle.createQuery(UserStatements.GET_USERS)
            .bind("role", role)
            .bind("skip", skip)
            .bind("limit", limit)
            .mapTo(UserItem::class.java)
            .list()

        return PaginationResult(
            total = if (users.isEmpty()) 0 else users[0].count!!,
            results = users
        )
    }

    override fun updateUser(id: Int, name: String?, avatarUrl: String?) {
        handle.createUpdate(UserStatements.UPDATE_USER)
            .bind("id", id)
            .bind("name", name)
            .bind("avatar_url", avatarUrl)
            .execute()
    }

    override fun deleteUser(id: Int) {
        handle.createUpdate(UserStatements.DELETE_USER)
            .bind("id", id)
            .execute()
    }

    override fun createToken(tokenValue: String, userId: Int): Token {
        return handle.createUpdate(UserStatements.CREATE_TOKEN)
            .bind("token_value", tokenValue)
            .bind("user_id", userId)
            .executeAndReturnGeneratedKeys()
            .mapTo<Token>()
            .one()
    }

    override fun getUserAndTokenByTokenValue(token: String): AuthenticatedUser? {
        return handle.createQuery(UserStatements.GET_USER_AND_TOKEN_BY_TOKEN_VALUE)
            .bind("token_value", token)
            .mapTo(UserAndToken::class.java)
            .singleOrNull()
            ?.userAndToken
    }

    override fun getTokenByUserId(userId: Int): Token? {
        return handle.createQuery(UserStatements.GET_TOKEN_BY_USER_ID)
            .bind("user_id", userId)
            .mapTo(Token::class.java)
            .singleOrNull()
    }

    override fun updateTokenLastUsed(token: Token, now: LocalDateTime) {
        handle.createUpdate(UserStatements.UPDATE_TOKEN_LAST_USED)
            .bind("token_value", token.tokenValue)
            .bind("last_used", now)
            .execute()
    }

    override fun deleteToken(token: String) {
        handle.createUpdate(UserStatements.DELETE_TOKEN)
            .bind("token_value", token)
            .execute()
    }

}