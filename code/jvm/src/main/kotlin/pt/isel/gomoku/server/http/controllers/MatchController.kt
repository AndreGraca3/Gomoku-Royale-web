package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.services.MatchService
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.problem.MatchProblem
import pt.isel.gomoku.server.services.error.match.MatchCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success
import java.util.UUID

@RestController
@RequestMapping(Uris.Matches.BASE)
class MatchController(private val service: MatchService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMatch(@RequestBody input: MatchCreateInputModel): ResponseEntity<*> {
        return when(val res = service.createMatch(input)){
            is Success -> ResponseEntity.status(201).body(res.value)
            is Failure -> when(res.value){
                is MatchCreationError.InvalidVariant -> MatchProblem.InvalidVariant(res.value).response()
                is MatchCreationError.InvalidPlayerInMatch -> MatchProblem.InvalidPlayerInMatch(res.value).response()
            }
        }
    }

    @GetMapping(Uris.ID)
    fun getMatchById(@PathVariable id: UUID): ResponseEntity<*> {
        return when(val res = service.getMatchById(id)) {
            is Success -> ResponseEntity.status(200).body(res.value)
            is Failure -> MatchProblem.InvalidMatchId(res.value).response()
        }
    }
}