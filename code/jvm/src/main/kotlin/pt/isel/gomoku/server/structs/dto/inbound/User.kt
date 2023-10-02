package pt.isel.gomoku.server.structs.dto.inbound

data class UserIn(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?
)