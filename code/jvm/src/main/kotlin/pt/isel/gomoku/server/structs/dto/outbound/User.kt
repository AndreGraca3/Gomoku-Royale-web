package pt.isel.gomoku.server.structs.dto.outbound

data class UserDTO(val id: Int)

data class UserOUT(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?,
    val rank: Int,
    val numberOfGames: Int
)