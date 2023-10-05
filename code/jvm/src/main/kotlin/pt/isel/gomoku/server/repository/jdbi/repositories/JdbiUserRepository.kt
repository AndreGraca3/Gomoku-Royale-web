package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.http.model.user.UserAndToken
import pt.isel.gomoku.server.http.model.user.UserCredentialsOutput
import pt.isel.gomoku.server.http.model.user.UserIdAndName
import pt.isel.gomoku.server.http.model.user.UserNameAndAvatar
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

    override fun getUserById(id: Int): UserNameAndAvatar? {
        return handle.createQuery(UserStatements.GET_USER_BY_ID)
            .bind("id", id)
            .mapTo(UserNameAndAvatar::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getUserByName(name: String): UserNameAndAvatar? {
        return handle.createQuery(UserStatements.GET_USER_BY_NAME)
            .bind("name", name)
            .mapTo(UserNameAndAvatar::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getUserByEmail(email: String): User? {
        return handle.createQuery(UserStatements.GET_USER_BY_EMAIL)
            .bind("email", email)
            .mapTo(User::class.java)
            .singleOrNull()
    }

    override fun getUsers(role: String?): List<UserIdAndName> {
        return handle.createQuery(UserStatements.GET_USERS)
            .bind("role", role)
            .mapTo(UserIdAndName::class.java)
            .list()
    }

    override fun updateUser(id: Int, name: String?, avatarUrl: String?, role: String?) {
        handle.createUpdate(UserStatements.UPDATE_USER)
            .bind("id", id)
            .bind("name", name)
            .bind("avatar_url", avatarUrl)
            .bind("role", role)
            .execute()
    }

    override fun deleteUser(id: Int) {
        handle.createUpdate(UserStatements.DELETE_USER)
            .bind("id", id)
            .execute()
    }

    override fun createToken(token: Token) {
        handle.createUpdate(UserStatements.CREATE_TOKEN)
            .bind("token_value", token.tokenValue)
            .bind("created_at", token.createdAt)
            .bind("last_used", token.lastUsed)
            .bind("user_id", token.userId)
            .execute()
    }

    override fun getUserAndTokenByTokenValue(token: String): Pair<User, Token>? {
        return handle.createQuery(UserStatements.GET_USER_AND_TOKEN_BY_TOKEN_VALUE)
            .bind("token_value", token)
            .mapTo(UserAndToken::class.java)
            .singleOrNull()
            ?.userAndToken
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