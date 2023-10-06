package pt.isel.gomoku.server.http.model.user


data class UserUpdateInput(
    val name: String,
    val avatarUrl: String?,
    val roleChangeRequest: UserRoleChangeRequest?
)