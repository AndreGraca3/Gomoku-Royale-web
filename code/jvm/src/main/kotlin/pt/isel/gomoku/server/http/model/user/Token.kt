package pt.isel.gomoku.server.http.model.user

import java.time.LocalDateTime

class Token(
    val tokenValue: String,
    val userId: Int,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime
)