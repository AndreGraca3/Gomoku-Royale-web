package pt.isel.gomoku.server.http.model

import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.server.http.response.siren.SirenClass

class MatchCreationOutputModel(val id: String, val state: String): OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

class MatchOutputModel(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
    val state: String,
    val board: Board
): OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match, SirenClass.info)
}

class MatchItemOutputModel(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
    val boardSize: Int
): OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

class MatchesFromUserOutputModel(val matches: List<MatchItemOutputModel>): OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match, SirenClass.collection)
}

class PlayOutputModel(val stone: Stone, val matchState: String): OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

data class MatchCreationInputModel(
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?
)