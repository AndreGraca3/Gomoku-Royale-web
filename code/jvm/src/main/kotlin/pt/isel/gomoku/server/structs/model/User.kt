package pt.isel.gomoku.server.structs.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?,
    val rank: Int,
    val numberOfGames: Int
)