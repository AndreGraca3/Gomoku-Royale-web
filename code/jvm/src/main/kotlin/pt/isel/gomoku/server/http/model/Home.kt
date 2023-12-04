package pt.isel.gomoku.server.http.model

import pt.isel.gomoku.server.http.response.siren.SirenClass
import java.time.OffsetDateTime

class HomeOutputModel(
    val name: String,
    val description: String,
    val version: Double,
    val uptimeMs: Long,
    val time: OffsetDateTime,
    val authors: List<String>
) : OutputModel() {
    override fun getSirenClasses() = listOf(SirenClass.home)
}