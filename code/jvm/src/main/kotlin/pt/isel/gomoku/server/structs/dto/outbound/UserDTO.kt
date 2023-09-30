package pt.isel.gomoku.server.structs.dto.outbound

data class UserDTO(val id: Int)

data class UserIn(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?
)

data class UserOUT(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?,
    val rank: Int,
    val numberOfGames: Int
)/* {
    constructor() : this(null, null, null, null, null, null)
}*/
