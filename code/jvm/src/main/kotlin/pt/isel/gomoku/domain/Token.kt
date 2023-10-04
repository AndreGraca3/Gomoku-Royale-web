package pt.isel.gomoku.domain

import java.time.LocalDateTime

class Token(
    val value: String,
    val userId: Int,
    val createdAt: LocalDateTime,
    val lastUsedAt: LocalDateTime
)