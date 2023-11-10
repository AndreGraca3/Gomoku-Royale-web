package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.domain.game.MatchState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.match.MatchCreationInput
import pt.isel.gomoku.server.http.model.problem.MatchProblem
import pt.isel.gomoku.server.http.model.siren.SirenEntity
import pt.isel.gomoku.server.http.model.siren.SirenLink
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.CREATE_PUBLIC_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.JOIN_PRIVATE_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.DELETE_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.GET_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.PLAY_MATCH_ACTION
import pt.isel.gomoku.server.http.model.user.AuthenticatedUser
import pt.isel.gomoku.server.service.MatchService
import pt.isel.gomoku.server.service.error.match.MatchCreationError
import pt.isel.gomoku.server.service.error.match.MatchFetchingError
import pt.isel.gomoku.server.service.error.match.MatchJoiningError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success

@RestController
@RequestMapping(Uris.Matches.BASE)
class MatchController(private val service: MatchService) {

    @PostMapping()
    fun createMatch(@RequestBody input: MatchCreationInput, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res =
            service.createMatch(authenticatedUser.user.id, input.isPrivate, input.size, input.variant)
        ) {
            is Success -> {
                val actionsList = mutableListOf(
                    GET_MATCH_ACTION,
                )
                when (res.value.state) {
                    MatchState.SETUP.name -> {
                        actionsList.add(JOIN_PRIVATE_MATCH_ACTION)
                        actionsList.add(DELETE_MATCH_ACTION)
                    }
                    MatchState.ONGOING.name -> actionsList.add(PLAY_MATCH_ACTION)
                }

                ResponseEntity.status(201).body(
                    SirenEntity(
                        clazz = listOf("match"),
                        properties = res.value,
                        links = listOf(
                            SirenLink.self(
                                href = "${Uris.Matches.BASE}/${res.value.id}"
                            )
                        ),
                        actions = actionsList
                    )
                )
            }

            is Failure -> when (res.value) {
                is MatchCreationError.InvalidVariant -> MatchProblem.InvalidVariant(res.value).response()
                is MatchCreationError.InvalidBoardSize -> MatchProblem.InvalidBoardSize(res.value).response()
                is MatchCreationError.AlreadyInQueue -> MatchProblem.AlreadyInQueue(res.value).response()
                is MatchCreationError.AlreadyInMatch -> MatchProblem.AlreadyInMatch(res.value).response()
                is MatchCreationError.InvalidPrivateMatch -> MatchProblem.InvalidPrivateMatch(res.value).response()
            }
        }
    }

    @PutMapping(Uris.ID)
    fun joinPrivateMatch(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.joinPrivateMatch(id, authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("match"),
                    properties = res.value,
                    links = listOf(
                        SirenLink.self(
                            href = "${Uris.Matches.BASE}/${res.value.id}"
                        )
                    ),
                    actions = listOf(
                        CREATE_PUBLIC_MATCH_ACTION,
                        GET_MATCH_ACTION,
                        PLAY_MATCH_ACTION
                    )
                )
            )

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).response()
                is MatchJoiningError.MatchIsNotPrivate -> MatchProblem.MatchIsNotPrivate(res.value).response()
                is MatchCreationError.AlreadyInQueue -> MatchProblem.AlreadyInQueue(res.value).response()
                else -> throw IllegalStateException("Unexpected error")
            }
        }
    }

    @GetMapping(Uris.ID)
    fun getMatchById(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.getMatchById(id, authenticatedUser.user.id)) {
            is Success -> {
                val actionsList = mutableListOf(JOIN_PRIVATE_MATCH_ACTION)
                when (res.value.state) {
                    MatchState.SETUP -> {
                        actionsList.add(DELETE_MATCH_ACTION)
                        if (res.value.isPrivate) actionsList.add(JOIN_PRIVATE_MATCH_ACTION)
                    }
                    MatchState.ONGOING -> actionsList.add(PLAY_MATCH_ACTION)
                    else -> Unit
                }

                ResponseEntity.status(200).body(
                    SirenEntity(
                        clazz = listOf("match"),
                        properties = res.value,
                        links = listOf(
                            SirenLink.self(
                                href = Uris.Matches.BASE + Uris.ID
                            )
                        ),
                        actions = actionsList
                    )
                )
            }

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).response()
                is MatchFetchingError.UserNotInMatch -> MatchProblem.UserNotInMatch(res.value).response()
            }
        }
    }

    @GetMapping()
    fun getMatchesFromUser(authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return ResponseEntity.status(200).body(
            SirenEntity(
                clazz = listOf("match"),
                properties = service.getMatchesFromUser(authenticatedUser.user.id),
                links = listOf(
                    SirenLink.self(
                        href = Uris.Matches.BASE + Uris.ID
                    )
                ),
                actions = listOf(
                    CREATE_PUBLIC_MATCH_ACTION,
                    GET_MATCH_ACTION,
                )
            )
        )
    }

    @PostMapping(Uris.ID)
    fun play(
        authenticatedUser: AuthenticatedUser,
        @PathVariable id: String,
        @RequestBody move: Dot,
    ): ResponseEntity<*> {
        return when (val res = service.play(authenticatedUser.user.id, id, move)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("match"),
                    properties = service.getMatchesFromUser(authenticatedUser.user.id),
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Matches.BASE + Uris.ID
                        )
                    ),
                    actions = listOf(
                        GET_MATCH_ACTION,
                    )
                )
            )

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).response()
                is MatchFetchingError.UserNotInMatch -> MatchProblem.UserNotInMatch(res.value).response()
            }
        }
    }

    @DeleteMapping
    fun deleteMatch(
        authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<*> {
        return ResponseEntity.status(204).body(
            SirenEntity(
                clazz = listOf("match"),
                properties = service.getMatchesFromUser(authenticatedUser.user.id),
                links = listOf(
                    SirenLink.self(
                        href = Uris.Matches.BASE + Uris.ID
                    )
                ),
                actions = listOf(
                    CREATE_PUBLIC_MATCH_ACTION,
                )
            )
        )
    }
}