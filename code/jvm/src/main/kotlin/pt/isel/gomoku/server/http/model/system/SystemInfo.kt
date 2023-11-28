package pt.isel.gomoku.server.http.model.system

import pt.isel.gomoku.server.http.model.user.UserIdAndName

class SystemInfo(
    val version: Float,
    val devs: List<UserIdAndName>
)