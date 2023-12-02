package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.domain.MatchState
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.MatchCreationInputModel
import pt.isel.gomoku.server.http.model.MatchOutputModel
import pt.isel.gomoku.server.http.response.problem.MatchProblem
import pt.isel.gomoku.server.http.response.siren.*
import pt.isel.gomoku.server.http.response.siren.actions.MatchActions.getDeleteMatchAction
import pt.isel.gomoku.server.http.response.siren.actions.MatchActions.getJoinPrivateMatchAction
import pt.isel.gomoku.server.http.response.siren.actions.MatchActions.getPlayMatchAction
import pt.isel.gomoku.server.repository.dto.AuthenticatedUser
import pt.isel.gomoku.server.service.MatchService
import pt.isel.gomoku.server.service.errors.match.MatchCreationError
import pt.isel.gomoku.server.service.errors.match.MatchFetchingError
import pt.isel.gomoku.server.service.errors.match.MatchJoiningError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success
import java.net.URI

@RestController
class MatchController(private val service: MatchService) {

    @PostMapping(Uris.Matches.BASE)
    fun createMatch(
        @RequestBody input: MatchCreationInputModel,
        authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<*> {
        return when (val res =
            service.createMatch(authenticatedUser.user.id, input.isPrivate, input.size, input.variant)
        ) {
            is Success -> {
                res.value.toSirenObject(
                    links = listOf(
                        SirenLink.self(href = Uris.Matches.buildMatchByIdUri(res.value.id))
                    ),
                    actions = when (res.value.state) {
                        MatchState.SETUP.name -> listOf(
                            getJoinPrivateMatchAction(res.value.id),
                            getDeleteMatchAction()
                        )

                        MatchState.ONGOING.name -> listOf(getPlayMatchAction(res.value.id))
                        else -> null
                    },
                ).toResponseEntity(201)
            }

            is Failure -> when (res.value) {
                is MatchCreationError.InvalidVariant -> MatchProblem.InvalidVariant(res.value).toResponseEntity()
                is MatchCreationError.InvalidBoardSize -> MatchProblem.InvalidBoardSize(res.value).toResponseEntity()
                is MatchCreationError.AlreadyInQueue -> MatchProblem.AlreadyInQueue(res.value).toResponseEntity()
                is MatchCreationError.AlreadyInMatch -> MatchProblem.AlreadyInMatch(res.value).toResponseEntity()
                is MatchCreationError.InvalidPrivateMatch -> MatchProblem.InvalidPrivateMatch(res.value)
                    .toResponseEntity()
            }
        }
    }

    @PutMapping(Uris.Matches.MATCH_BY_ID)
    fun joinPrivateMatch(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.joinPrivateMatch(id, authenticatedUser.user.id)) {
            is Success -> res.value.toSirenObject(
                links = listOf(
                    SirenLink.self(href = Uris.Matches.buildMatchByIdUri(res.value.id))
                ),
                actions = listOf(
                    getPlayMatchAction(res.value.id)
                ),
            ).toResponseEntity(200)

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).toResponseEntity()
                is MatchJoiningError.MatchIsNotPrivate -> MatchProblem.MatchIsNotPrivate(res.value).toResponseEntity()
                else -> throw IllegalStateException("Unexpected error")
            }
        }
    }

    @GetMapping(Uris.Matches.MATCH_BY_ID)
    fun getMatchById(@PathVariable id: String, authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.getMatchById(id, authenticatedUser.user.id)) {
            is Success -> {
                val actions = mutableListOf<SirenAction>()
                when (res.value.state) {
                    MatchState.SETUP -> {
                        actions.add(getDeleteMatchAction())
                        if (res.value.isPrivate) actions.add(getJoinPrivateMatchAction(res.value.id))
                    }

                    MatchState.ONGOING -> actions.add(getPlayMatchAction(res.value.id))
                    else -> Unit
                }

                val entities = listOfNotNull(
                    EmbeddedLink(
                        clazz = listOf(SirenClass.match),
                        rel = listOf("playerBlack"),
                        href = Uris.Users.buildUuserByIdUri(res.value.blackId),
                    ),
                    if (res.value.whiteId != null)
                        EmbeddedLink(
                            clazz = listOf(SirenClass.match),
                            rel = listOf("playerWhite"),
                            href = Uris.Users.buildUuserByIdUri(res.value.whiteId),
                        )
                    else null
                )

                MatchOutputModel(
                    res.value.id,
                    res.value.isPrivate,
                    res.value.variant.toString(),
                    res.value.state.toString(),
                    res.value.board,
                ).toSirenObject(
                    links = listOf(
                        SirenLink.self(href = Uris.Matches.buildMatchByIdUri(res.value.id))
                    ),
                    entities = entities,
                    actions = actions,
                ).toResponseEntity(200)
            }

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).toResponseEntity()
                is MatchFetchingError.UserNotInMatch -> MatchProblem.UserNotInMatch(res.value).toResponseEntity()
            }
        }
    }

    @GetMapping(Uris.Matches.BASE)
    fun getMatchesFromUser(authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return service.getMatchesFromUser(authenticatedUser.user.id).toSirenObject(
            links = listOf(
                SirenLink.self(href = URI(Uris.Matches.BASE))
            )
        ).toResponseEntity(200)
    }

    @PostMapping(Uris.Matches.MATCH_BY_ID)
    fun play(
        authenticatedUser: AuthenticatedUser,
        @PathVariable id: String,
        @RequestBody move: Dot,
    ): ResponseEntity<*> {
        return when (val res = service.play(authenticatedUser.user.id, id, move)) {
            is Success ->
                res.value.toSirenObject(
                    links = listOf(
                        SirenLink.self(href = Uris.Matches.buildMatchByIdUri(id))
                    ),
                ).toResponseEntity(200)

            is Failure -> when (res.value) {
                is MatchFetchingError.MatchByIdNotFound -> MatchProblem.MatchNotFound(res.value).toResponseEntity()
                is MatchFetchingError.UserNotInMatch -> MatchProblem.UserNotInMatch(res.value).toResponseEntity()
            }
        }
    }

    @DeleteMapping
    fun deleteSetupMatch(
        authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<*> {
        service.deleteSetupMatch(authenticatedUser.user.id)
        return ResponseEntity.status(200).body(null)
    }
}