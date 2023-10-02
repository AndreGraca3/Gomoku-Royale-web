package pt.isel.gomoku.server.structs.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: Role = Role.USER,
    val avatar: String?,
    val rank: String
)

enum class Role(val value: String) {
    USER("user"), DEV("dev")
}