package pt.isel.gomoku.server.http.model.match

import pt.isel.gomoku.domain.game.Board
import pt.isel.gomoku.domain.game.Stone

data class BoardCreateInputModel(
    val variant: String,
    val size: Int,
    val turn: String,
){
    override fun toString(): String {
        return "${variant}\n${size}\n${turn}"
    }
}


