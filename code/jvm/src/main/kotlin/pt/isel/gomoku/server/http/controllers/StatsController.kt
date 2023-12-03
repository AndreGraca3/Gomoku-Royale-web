package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.PaginationInputs
import pt.isel.gomoku.server.http.model.TopRanksOutputModel
import pt.isel.gomoku.server.http.model.toOutputModel
import pt.isel.gomoku.server.http.response.siren.SirenLink
import pt.isel.gomoku.server.service.StatsService
import java.net.URI

@RestController
class StatsController(private val service: StatsService) {

    @GetMapping(Uris.Stats.TOP)
    fun getTopRanks(paginationInputs: PaginationInputs): ResponseEntity<*> {
        val topUsersCollection = service.getTopRanks(paginationInputs.skip, paginationInputs.limit)
        return TopRanksOutputModel(
            ranks = topUsersCollection.results.map { it.toOutputModel() },
            total = topUsersCollection.total
        ).toSirenObject(
            links = listOf(
                SirenLink.self(href = URI(Uris.Stats.TOP))
            )
        ).toResponseEntity(200)
    }

    @GetMapping(Uris.Stats.STATS_BY_USER_ID)
    fun getUserStats(@PathVariable id: Int): ResponseEntity<*> {
        return service.getUserStats(id).toSirenObject(
            links = listOf(
                SirenLink.self(href = Uris.Stats.buildStatsByUserIdUri(id)),
                SirenLink(listOf("user"), href = Uris.Users.buildUserByIdUri(id))
            )
        ).toResponseEntity(200)
    }
}