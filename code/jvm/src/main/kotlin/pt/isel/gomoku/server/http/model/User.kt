package pt.isel.gomoku.server.http.model

import pt.isel.gomoku.domain.Rank
import pt.isel.gomoku.server.http.response.siren.SirenClass
import pt.isel.gomoku.server.repository.dto.UserInfo
import pt.isel.gomoku.server.repository.dto.UserItem
import java.time.LocalDateTime

class UserIdOutputModel(val id: Int) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.user)
}

class UserItemOutputModel(
    val id: Int,
    val name: String,
    val role: String,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.user)
}

fun UserItem.toOutputModel() = UserItemOutputModel(
    id = id,
    name = name,
    role = role,
)

class UserInfoOutputModel(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val role: String,
    val rank: Rank,
    val createdAt: LocalDateTime,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.user, SirenClass.info)
}

fun UserInfo.toOutputModel() = UserInfoOutputModel(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    role = role,
    rank = rank,
    createdAt = createdAt,
)

class UsersOutputModel(
    val users: List<UserItemOutputModel>,
    val total: Int,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.user, SirenClass.collection)
}

data class UserCreationInputModel(
    val name: String,
    val email: String,
    val password: String,
    val avatarUrl: String?,
)

data class TokenCreationOutput(
    val token: String,
)

data class UserCredentialsInput(
    val email: String,
    val password: String,
)

data class UserUpdateInputModel(
    val name: String,
    val avatarUrl: String?,
)