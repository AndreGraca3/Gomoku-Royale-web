package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.services.MatchService
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchUpdateInput
import pt.isel.gomoku.server.http.model.problem.MatchProblem
import pt.isel.gomoku.server.http.model.user.AuthenticatedUser
import pt.isel.gomoku.server.services.error.match.MatchCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success
import java.util.UUID

@RestController
@RequestMapping(Uris.Matches.BASE)
class MatchController(private val service: MatchService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMatch(
        @RequestBody input: MatchCreateInputModel,
        authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<*> {
        return when (val res = service.createMatch(authenticatedUser.user.id, input.isPrivate, input.size, input.variant)) {
            is Success -> ResponseEntity.status(201).body(res.value)
            is Failure -> when (res.value) {
                is MatchCreationError.InvalidVariant -> MatchProblem.InvalidVariant(res.value).response()
                is MatchCreationError.InvalidPlayerInMatch -> MatchProblem.InvalidPlayerInMatch(res.value).response()
            }
        }
    }

    // Qualquer auth user pode aceder a qualquer match, mesmo que não seja dele?
    // Response: Interceptor

    @GetMapping(Uris.ID)
    fun getMatchById(
        @PathVariable id: UUID,
        authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<*> {
        return when (val res = service.getMatchById(id, authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).body(res.value)
            is Failure -> MatchProblem.InvalidMatchId(res.value).response()
        }
    }

    // TODO() -> Can only update match winner only if the match has 2 players
    @PutMapping("")
    fun updateMatch(
        authenticatedUser: AuthenticatedUser,
        @RequestBody matchInput: MatchUpdateInput
    ): ResponseEntity<*> {
        return when (val res =
            service.updateMatch(matchInput.id, matchInput.visibility, matchInput.winner, authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).build<Unit>()
            is Failure -> MatchProblem.InvalidValues(res.value).response()
        }
    }

//    @PostMapping(Uris.ID)
//    fun playMove(
//        authenticatedUser: AuthenticatedUser,
//        @PathVariable idMatch: UUID,
//        @RequestBody move: String
//    ): ResponseEntity<*> {
//        return when (val res =
//            service.playMove(authenticatedUser.user.id, idMatch, move)) {
//            is Success -> ResponseEntity.status(200).build<Unit>()
//            is Failure -> MatchProblem.InvalidValues(res.value).response()
//        }
//    }

//    @GetMapping(Uris.ID+"/all")
//    fun getMatchesFromUser(
//        @PathVariable id: Int,
//        authenticatedUser: AuthenticatedUser,
//    ): ResponseEntity<*> {
//        return ResponseEntity.status(200).body(service.getMatchesFromUser(id))
//    }
}