package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.siren.SirenEntity
import pt.isel.gomoku.server.http.model.siren.SirenLink
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions
import pt.isel.gomoku.server.http.model.siren.actions.StatsAction.GET_TOP_STATS_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.StatsAction.GET_USER_STATS_ACTION
import pt.isel.gomoku.server.service.StatsService

@RestController
@RequestMapping(Uris.Stats.BASE)
class StatsController(private val service: StatsService) {

    @GetMapping(Uris.Stats.TOP)
    fun getTopRanks(@RequestParam limit: Int?): ResponseEntity<*> {
        return ResponseEntity.status(200).body(
            SirenEntity(
                clazz = listOf("stats"),
                properties = service.getTopRanks(limit),
                links = listOf(
                    SirenLink.self(
                        href = Uris.Stats.BASE + Uris.ID
                    )
                ),
                actions = listOf(
                    GET_USER_STATS_ACTION
                )
            )
        )
    }

    @GetMapping("${Uris.Users.BASE}${Uris.ID}")
    fun getUserStats(@PathVariable id: Int): ResponseEntity<*> {
        return service.getUserStats(id).let {
            ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("stats"),
                    properties = it,
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Stats.BASE + Uris.ID
                        )
                    ),
                    actions = listOf(
                        GET_TOP_STATS_ACTION
                    )
                )
            )
        }
    }
}