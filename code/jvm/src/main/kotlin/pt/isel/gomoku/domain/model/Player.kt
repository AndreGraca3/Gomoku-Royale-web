package pt.isel.gomoku.domain.model

enum class Player {
    BLACK, WHITE;
    fun opposite() = if(this == BLACK) WHITE else BLACK
}