package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.services.MatchService
import pt.isel.gomoku.server.http.model.match.MatchIn
import pt.isel.gomoku.server.http.model.match.MatchOut

@RestController
@RequestMapping("/matches")
class MatchController(private val service: MatchService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody matchIn: MatchIn): Int = service.createMatch(matchIn)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): MatchOut = service.getMatch(id)
}