package pt.isel.gomoku.server.http.model.user

// TODO: discuss this
data class UserRoleChangeRequest(
    val role: String,
    val apiSecret: String
)