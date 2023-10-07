package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.game.Board

class MatchCreateInputModel(
    val visibility: String,
    val boardSpecs: BoardCreateInputModel,
    val variant: String,
    //val created_at: String,
    val player1_id: Int,
    //val player2_id: Int,
    //val winner_id: Int
)


