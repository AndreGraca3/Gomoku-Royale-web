package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
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
            .findFirst()
            .orElse(null)
    }

    override fun getUsers(role: String?): List<UserIdAndName> {
        return handle.createQuery(UserStatements.GET_USERS)
            .bind("role", role)
            .mapTo(UserIdAndName::class.java)
            .list()
    }

    override fun updateUser(name: String, avatarUrl: String?, role: String?) {
        handle.createUpdate(UserStatements.UPDATE_USER)
            .bind("avatar_url", avatarUrl)
            .bind("role", role)
            .bind("name", name)
            .execute()
    }

    override fun deleteUser(id: Int) {
        handle.createUpdate(UserStatements.DELETE_USER)
            .bind("id", id)
            .execute()
    }

    override fun createToken(token: Token) {
        handle.createUpdate(UserStatements.CREATE_TOKEN)
            .bind("value", token.tokenValue)
            .bind("created_at", token.createdAt)
            .bind("last_used", token.lastUsed)
            .bind("user_id", token.userId)
            .execute()
    }

    override fun getUserAndTokenByTokenValue(token: String): Pair<User, Token>? {
        TODO("Not yet implemented")
    }

    override fun updateTokenLastUsed(token: Token, now: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun deleteToken(token: String) {
        TODO("Not yet implemented")
    }

}