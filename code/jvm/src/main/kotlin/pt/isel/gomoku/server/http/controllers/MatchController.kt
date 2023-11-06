package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.match.MatchCreationInput
import pt.isel.gomoku.server.http.model.problem.MatchProblem
import pt.isel.gomoku.server.http.model.siren.SirenEntity
import pt.isel.gomoku.server.http.model.siren.SirenLink
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.CREATE_MATCH_ACTION_PUBLIC
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.JOIN_PRIVATE_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.DELETE_MATCH_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.MatchActions.GET_MATCHES_FROM_USER_ACTION
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
    @ResponseStatus(HttpStatus.CREATED)
    fun createMatch(@RequestBody input: MatchCreationInput, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res =
            service.createMatch(authenticatedUser.user.id, input.isPrivate, input.size, input.variant)
        ) {
            is Success -> ResponseEntity.status(201).body(
                SirenEntity(
                    clazz = listOf("match"),
                    properties = res.value,
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Matches.BASE + Uris.ID
                        )
                    ),
//                    actions = getSirenActionListFrom(MatchController::class.java, "createMatch", res.value)
                    actions = listOf(
                        JOIN_PRIVATE_MATCH_ACTION,
                        GET_MATCH_ACTION,
                        DELETE_MATCH_ACTION,
                        GET_MATCHES_FROM_USER_ACTION,
                        PLAY_MATCH_ACTION
                    )
                )
            )
            is Failure -> when (res.value) {
                is MatchCreationError.InvalidVariant -> MatchProblem.InvalidVariant(res.value).response()
                is MatchCreationError.InvalidBoardSize -> MatchProblem.InvalidBoardSize(res.value).response()
                is MatchCreationError.AlreadyInQueue -> MatchProblem.AlreadyInQueue(res.value).response()
                is MatchCreationError.AlreadyInMatch -> MatchProblem.AlreadyInMatch(res.value).response()
                is MatchCreationError.InvalidPrivateMatch -> MatchProblem.InvalidPrivateMatch(res.value).response()
            }
        }
    }

    @PostMapping("${Uris.ID}${Uris.Matches.JOIN}")
    fun joinPrivateMatch(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.joinPrivateMatch(id, authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(201).body(
                SirenEntity(
                    clazz = listOf("match"),
                    properties = res.value,
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Matches.BASE + Uris.ID
                        )
                    ),
                    actions = listOf(
                        CREATE_MATCH_ACTION_PUBLIC,
                        GET_MATCH_ACTION,
                        DELETE_MATCH_ACTION,
                        GET_MATCHES_FROM_USER_ACTION,
                        PLAY_MATCH_ACTION
                    )
                )
            )
            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).response()
                is MatchJoiningError.MatchIsNotPrivate -> MatchProblem.MatchIsNotPrivate(res.value).response()
                is MatchCreationError.AlreadyInQueue -> MatchProblem.AlreadyInQueue(res.value).response()
                else -> throw Exception()
            }
        }
    }

    @GetMapping(Uris.ID)
    fun getMatchById(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.getMatchById(id, authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("match"),
                    properties = res.value,
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Matches.BASE + Uris.ID
                        )
                    ),
                    actions = listOf(
                        CREATE_MATCH_ACTION_PUBLIC,
                        JOIN_PRIVATE_MATCH_ACTION,
                        DELETE_MATCH_ACTION,
                        GET_MATCHES_FROM_USER_ACTION,
                        PLAY_MATCH_ACTION
                    )
                )
            )
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
                    CREATE_MATCH_ACTION_PUBLIC,
                    JOIN_PRIVATE_MATCH_ACTION,
                    DELETE_MATCH_ACTION,
                    GET_MATCH_ACTION,
                    PLAY_MATCH_ACTION
                )
            )
        )
    }

    @PostMapping(Uris.ID)
    fun play(
        authenticatedUser: AuthenticatedUser,
        @PathVariable id: String,
        @RequestBody move: Dot
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
                        CREATE_MATCH_ACTION_PUBLIC,
                        JOIN_PRIVATE_MATCH_ACTION,
                        DELETE_MATCH_ACTION,
                        GET_MATCH_ACTION,
                        GET_MATCHES_FROM_USER_ACTION
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
        authenticatedUser: AuthenticatedUser
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
                    CREATE_MATCH_ACTION_PUBLIC,
                    JOIN_PRIVATE_MATCH_ACTION,
                    GET_MATCH_ACTION,
                    GET_MATCHES_FROM_USER_ACTION,
                    PLAY_MATCH_ACTION
                )
            )
        )
    }
}