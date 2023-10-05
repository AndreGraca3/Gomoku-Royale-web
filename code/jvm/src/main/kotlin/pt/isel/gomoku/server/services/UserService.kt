package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.SystemDomain
import pt.isel.gomoku.domain.Token
import pt.isel.gomoku.domain.User
import pt.isel.gomoku.domain.UserDomain
import pt.isel.gomoku.server.http.model.user.AuthenticatedUser
import pt.isel.gomoku.server.http.model.user.UserNameAndAvatar
import pt.isel.gomoku.server.http.model.user.UserRoleChangeRequest
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.services.error.user.TokenCreationError
import pt.isel.gomoku.server.services.error.user.UserCreationError
import pt.isel.gomoku.server.services.error.user.UserFetchingError
import pt.isel.gomoku.server.services.error.user.UserUpdateError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success
import java.time.LocalDateTime

@Component
class UserService(
    private val trManager: TransactionManager,
    private val userDomain: UserDomain,
    private val systemDomain: SystemDomain
) {

    fun createUser(
        name: String,
        email: String,
        password: String,
        avatar: String?
    ): Either<UserCreationError, Int> {
        if (!userDomain.isSafePassword(password))
            return failure(UserCreationError.InsecurePassword(password))

        return trManager.run {
            val usersRepository = it.userRepository
            if (usersRepository.getUserByEmail(email) != null) {
                failure(UserCreationError.EmailAlreadyInUse(email))
            } else {
                val id = usersRepository.createUser(name, email, password, avatar)
                success(id)
            }
        }
    }

    fun getUsers(role: String? = null) = trManager.run { it.userRepository.getUsers(role) }

    fun getUserById(id: Int): Either<UserFetchingError.UserByIdNotFound, UserNameAndAvatar> {
        return trManager.run {
            val user: UserNameAndAvatar? = it.userRepository.getUserById(id)
            if (user != null) success(user)
            else failure(UserFetchingError.UserByIdNotFound(id))
        }
    }

    fun updateUser(
        id: Int,
        newName: String?,
        newAvatar: String?,
        roleChange: UserRoleChangeRequest
    ): Either<UserUpdateError.InvalidValues, Unit> {

        if (newName?.isBlank() == true || newAvatar?.isBlank() == true)
            return failure(UserUpdateError.InvalidValues)

        val newRole = when (roleChange.apiSecret) {
            systemDomain.apiSecret -> roleChange.role
            else -> null
        }

        return trManager.run {
            success(
                it.userRepository.updateUser(
                    id,
                    newName,
                    newAvatar,
                    newRole
                )
            )
        }
    }

    fun createToken(email: String, password: String): Either<TokenCreationError.InvalidCredentials, Token> {
        if (email.isBlank() || password.isBlank()) {
            failure(TokenCreationError.InvalidCredentials(email, password))
        }

        return trManager.run {
            val user: User = it.userRepository.getUserByEmail(email)
                ?: return@run failure(TokenCreationError.InvalidCredentials(email, password))
            if (password != user.password) {
                return@run failure(TokenCreationError.InvalidCredentials(email, password))
            }
            val tokenValue = userDomain.generateTokenValue()
            val now = LocalDateTime.now()
            val newToken = Token(
                tokenValue,
                user.id,
                createdAt = now,
                lastUsed = now
            )
            it.userRepository.createToken(newToken)
            success(newToken)
        }
    }

    // Service function, does not return Either
    fun getUserByToken(token: String): User? {
        return trManager.run {
            val userAndToken =
                it.userRepository.getUserAndTokenByTokenValue(token)
            if (userAndToken != null && userDomain.isTokenTimeValid(userAndToken.second)) {
                it.userRepository.updateTokenLastUsed(userAndToken.second, LocalDateTime.now())
                userAndToken.first
            } else null
        }
    }
}