package pt.isel.gomoku.domain

enum class Player(val symbol: Char) {
    BLACK('B'),
    WHITE('W');

    fun opposite() = if (this == BLACK) WHITE else BLACK
}

fun Char.toPlayer(): Player {
    return when (this) {
        'B' -> Player.BLACK
        'W' -> Player.WHITE
        else -> throw IllegalArgumentException("The player must be either B or W")
    }
}