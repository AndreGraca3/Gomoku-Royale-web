package pt.isel.gomoku.server.http.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import pt.isel.gomoku.domain.MatchState
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.board.Board
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.server.http.response.siren.SirenClass
import pt.isel.gomoku.server.repository.dto.MatchItem

class MatchCreationOutputModel(
    val id: String,
    @JsonSerialize(using = ToStringSerializer::class) val state: MatchState,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

class MatchOutputModel(
    val id: String,
    val blackId: Int,
    val whiteId: Int?,
    val isPrivate: Boolean,
    val variant: String,
    @JsonSerialize(using = ToStringSerializer::class) val state: MatchState,
    val board: Board,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match, SirenClass.info)
}

class MatchItemOutputModel(
    val id: String,
    val isPrivate: Boolean,
    val variant: String,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

fun MatchItem.toOutputModel() = MatchItemOutputModel(id, isPrivate, variant)

class MatchesFromUserOutputModel(
    val matches: List<MatchItemOutputModel>,
    val total: Int,
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match, SirenClass.collection)
}

class PlayOutputModel(val stone: Stone, val matchState: String, val turn: Player) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

class ForfeitOutputModel(val winner: Player, matchState: String) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.match)
}

data class MatchCreationInputModel(
    val isPrivate: Boolean,
    val size: Int?,
    val variant: String?,
)