package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.services.MatchService
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchOut

@RestController
@RequestMapping(Uris.Matches.BASE)
class MatchController(private val service: MatchService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMatch(@RequestBody input: MatchCreateInputModel): Int {
        TODO()
    }

    @GetMapping(Uris.ID)
    fun getMatchById(@PathVariable id: Int): MatchOut {
        TODO()
    }
}