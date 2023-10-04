package pt.isel.gomoku.server.http.model.user

data class UserOut(
    val id: Int,
    val name: String,
    val avatar: String?,
    val role: String,
)