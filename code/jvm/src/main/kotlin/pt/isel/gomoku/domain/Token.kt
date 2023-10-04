package pt.isel.gomoku.domain

import java.time.LocalDateTime

class Token(
    val tokenValue: String,
    val userId: Int,
    val createdAt: LocalDateTime,
    val lastUsed: LocalDateTime
)