package pt.isel.gomoku.server.structs.model

data class Play(
    val idUser: Int,
    val idMatch: Int,
    val color: String
)