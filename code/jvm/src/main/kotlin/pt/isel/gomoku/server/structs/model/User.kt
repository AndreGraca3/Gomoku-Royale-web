package pt.isel.gomoku.server.structs.model

data class User(
    val id: Int,
    val token: String,
    val email: String,
    val password: String,
    val name: String,
    val avatar: String?
)