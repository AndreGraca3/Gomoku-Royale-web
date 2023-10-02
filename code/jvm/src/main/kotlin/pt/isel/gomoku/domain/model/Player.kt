package pt.isel.gomoku.domain.model

enum class Player(val color: PlayerColor) {
    PLAYER_ONE(PlayerColor.BLACK), PLAYER_TWO(PlayerColor.WHITE);
    fun opposite() = if(this == PLAYER_ONE) PLAYER_TWO else PLAYER_ONE
}

enum class PlayerColor {
    BLACK, WHITE
}