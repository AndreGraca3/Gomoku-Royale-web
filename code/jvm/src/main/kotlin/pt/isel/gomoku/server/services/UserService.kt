package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.UserDomain
import pt.isel.gomoku.server.http.model.user.UserOut
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.services.error.UserCreationError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success

@Component
class UserService(private val trManager: TransactionManager, private val userDomain: UserDomain) {

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
            if (usersRepository.getUserByName(name) != null) {
                failure(UserCreationError.UserAlreadyExists(name))
            } else {
                val id = usersRepository.createUser(name, email, password, avatar)
                success(id)
            }
        }
    }

    fun getUsers(role: String? = null) = trManager.run { it.userRepository.getUsers(role) }

    fun getUser(id: Int): UserOut? = trManager.run { it.userRepository.getUserById(id) }

    fun updateUser(id: Int, newName: String, newAvatar: String?): UserOut {
        TODO()
    }

    fun createToken(username: String, password: String): String {
        TODO()
    }
}