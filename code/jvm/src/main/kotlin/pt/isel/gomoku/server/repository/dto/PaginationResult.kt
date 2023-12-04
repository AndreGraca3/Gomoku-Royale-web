package pt.isel.gomoku.server.repository.dto

data class PaginationResult<T>(
    val results: List<T>,
    val total: Int,
)