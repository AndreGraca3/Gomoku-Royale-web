package pt.isel.gomoku.server.repository.jdbi.repositories

import org.jdbi.v3.core.Handle
import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.http.model.user.UserCreateInputModel
import pt.isel.gomoku.server.http.model.user.UserIdAndName
import pt.isel.gomoku.server.http.model.user.UserOut
import pt.isel.gomoku.server.repository.interfaces.UserRepository
import pt.isel.gomoku.server.repository.jdbi.statements.UserStatements
import java.time.LocalDateTime

class JdbiUserRepository(private val handle: Handle) : UserRepository {

    override fun createUser(name: String, email: String, password: String, avatar: String?): Int {
        return handle.createUpdate(UserStatements.CREATE_USER)
            .bind("name", name)
            .bind("email", email)
            .bind("password", password)
            .bind("avatar", avatar)
            .executeAndReturnGeneratedKeys("id")
            .mapTo(Int::class.java)
            .one()
    }

    override fun getUserById(id: Int): UserOut? {
        return handle.createQuery(UserStatements.GET_USER_BY_ID)
            .bind("id", id)
            .mapTo(UserOut::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getUserByName(name: String): UserOut? {
        return handle.createQuery(UserStatements.GET_USER_BY_NAME)
            .bind("name", name)
            .mapTo(UserOut::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getUserByEmail(email: String): UserOut? {
        return handle.createQuery(UserStatements.GET_USER_BY_EMAIL)
            .bind("email", email)
            .mapTo(UserOut::class.java)
            .findFirst()
            .orElse(null)
    }

    override fun getUsers(role: String?): List<UserIdAndName> {
        return handle.createQuery(UserStatements.GET_USERS)
            .bind("role", role)
            .mapTo(UserIdAndName::class.java)
            .list()
    }

    override fun updateUser(input: UserCreateInputModel): UserOut {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Int) {
        TODO("Not yet implemented")
    }

    override fun createToken(token: Token, maxTokens: Int): Token {
        TODO("Not yet implemented")
    }

    override fun getUserByToken(token: String): User? {
        TODO("Not yet implemented")
    }

    override fun getTokenByValue(value: String): Token? {
        TODO("Not yet implemented")
    }

    override fun updateTokenLastUsed(token: Token, now: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun deleteToken(token: String) {
        TODO("Not yet implemented")
    }

}