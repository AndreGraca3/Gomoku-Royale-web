package pt.isel.gomoku.domain

import java.time.LocalDateTime

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val avatarUrl: String?,
    val rank: Rank,
    val createdAt: LocalDateTime
)