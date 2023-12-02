package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.server.repository.dto.Token
import pt.isel.gomoku.server.http.model.UserIdOutputModel
import pt.isel.gomoku.server.http.model.UsersOutputModel
import pt.isel.gomoku.server.http.model.toOutputModel
import pt.isel.gomoku.server.repository.dto.AuthenticatedUser
import pt.isel.gomoku.server.repository.dto.UserInfo
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.service.core.SecurityManager
import pt.isel.gomoku.server.service.errors.user.TokenCreationError
import pt.isel.gomoku.server.service.errors.user.UserCreationError
import pt.isel.gomoku.server.service.errors.user.UserFetchingError
import pt.isel.gomoku.server.service.errors.user.UserUpdateError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success
import java.time.LocalDateTime

@Component
class UserService(
    private val trManager: TransactionManager,
    private val securityManager: SecurityManager,
) {

    fun createUser(
        name: String,
        email: String,
        password: String,
        avatar: String?,
    ): Either<UserCreationError, UserIdOutputModel> {
        if (!securityManager.isSafePassword(password))
            return failure(UserCreationError.InsecurePassword(password))

        if (verifyEmail(email))
            return failure(UserCreationError.InvalidEmail(email))

        return trManager.run {
            if (it.userRepository.getUserByEmail(email) != null)
                return@run failure(UserCreationError.EmailAlreadyInUse(email))

            val id = it.userRepository.createUser(name, email, password, avatar)
            it.statsRepository.createStatsEntry(id)
            success(UserIdOutputModel(id))
        }
    }

    fun getUsers(role: String? = null, page: Int?, limit: Int?): UsersOutputModel {
        return trManager.run { transaction ->
            val usersCollection = transaction.userRepository.getUsers(role)
            UsersOutputModel(
                collectionSize = usersCollection.collectionSize,
                users = usersCollection.results.map { it.toOutputModel() },
                page = page ?: 0,
                limit = limit ?: usersCollection.collectionSize,
            )
        }
    }

    fun getUserById(id: Int): Either<UserFetchingError.UserByIdNotFound, UserInfo> {
        return trManager.run {
            val user = it.userRepository.getUserById(id)
            if (user != null) success(user)
            else failure(UserFetchingError.UserByIdNotFound(id))
        }
    }

    fun updateUser(id: Int, newName: String?, newAvatar: String?): Either<UserUpdateError.InvalidValues, Unit> {
        if (newName?.isBlank() == true || newAvatar?.isBlank() == true)
            return failure(UserUpdateError.InvalidValues)

        return trManager.run {
            success(it.userRepository.updateUser(id, newName, newAvatar))
        }
    }

    fun createToken(email: String, password: String): Either<TokenCreationError.InvalidCredentials, Token> {
        if (email.isBlank() || password.isBlank())
            failure(TokenCreationError.InvalidCredentials(email, password))

        return trManager.run {
            val user: User = it.userRepository.getUserByEmail(email)
                ?: return@run failure(TokenCreationError.InvalidCredentials(email, password))

            if (password != user.password)
                return@run failure(TokenCreationError.InvalidCredentials(email, password))

            // if token already exists: if is valid, return it else delete it
            val token = it.userRepository.getTokenByUserId(user.id)
            if (token != null) {
                when (securityManager.isTokenTimeValid(token)) {
                    true -> {
                        it.userRepository.updateTokenLastUsed(token, LocalDateTime.now())
                        return@run success(token)
                    }

                    false -> it.userRepository.deleteToken(token.tokenValue)
                }
            }

            // No Token found, create new one
            val tokenValue = securityManager.generateTokenValue()
            success(it.userRepository.createToken(tokenValue, user.id))
        }
    }

    fun deleteUser(id: Int) {
        return trManager.run {
            it.userRepository.deleteUser(id)
        }
    }

    // Helper function, does not return Either
    fun getUserByTokenValue(token: String): AuthenticatedUser? {
        return trManager.run {
            val userAndToken =
                it.userRepository.getUserAndTokenByTokenValue(token)
            if (userAndToken != null && securityManager.isTokenTimeValid(userAndToken.token)) {
                it.userRepository.updateTokenLastUsed(userAndToken.token, LocalDateTime.now())
                userAndToken
            } else null
        }
    }

    private fun verifyEmail(email: String) = !Regex("@").containsMatchIn(email)
}
