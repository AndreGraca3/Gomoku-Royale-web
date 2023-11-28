package pt.isel.gomoku.server.http.model.user

import pt.isel.gomoku.server.http.model.stats.Rank

data class UserDetails(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val role: String,
    val rank: Rank
)

fun User.toUserDetails() = UserDetails(
    id = id,
    name = name,
    email = email,
    avatarUrl = avatarUrl,
    role = role,
    rank = Rank("Silver", iconUrl = "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-4.png")
)