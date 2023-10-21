package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.service.StatsService

@RestController
@RequestMapping(Uris.Stats.BASE)
class StatsController(private val service: StatsService) {

    @GetMapping(Uris.Stats.TOP)
    fun getTopRanks(@RequestParam limit: Int?): ResponseEntity<*> {
        return ResponseEntity.status(200).body(service.getTopRanks(limit))
    }

    @GetMapping("${Uris.Users.BASE}${Uris.ID}")
    fun getUserStats(@PathVariable id: Int): ResponseEntity<*> {
        return service.getUserStats(id).let {
            ResponseEntity.status(200).body(it)
        }
    }
}