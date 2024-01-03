package pt.isel.gomoku.server.http.controllers

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.*
import pt.isel.gomoku.server.http.response.problem.UserProblem
import pt.isel.gomoku.server.http.response.siren.Siren
import pt.isel.gomoku.server.http.response.siren.SirenLink
import pt.isel.gomoku.server.http.response.siren.actions.UserActions.getDeleteUserAction
import pt.isel.gomoku.server.http.response.siren.actions.UserActions.getUpdateUserAction
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationDetails
import pt.isel.gomoku.server.repository.dto.AuthenticatedUser
import pt.isel.gomoku.server.service.UserService
import pt.isel.gomoku.server.service.errors.user.UserCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success
import java.net.URI

@RestController
class UserController(private val service: UserService) {

    @PostMapping(Uris.Users.BASE)
    fun createUser(@RequestBody input: UserCreationInputModel): ResponseEntity<*> {
        return when (val res = service.createUser(input.name, input.email, input.password, input.avatarUrl)) {
            is Success -> UserIdOutputModel(id = res.value.id)
                .toSirenObject(
                    links = listOf(
                        SirenLink.self(href = Uris.Users.buildUserByIdUri(res.value.id))
                    )
                ).toResponseEntity(201)

            is Failure -> when (res.value) {
                is UserCreationError.InsecurePassword -> UserProblem.InsecurePassword(res.value).toResponseEntity()
                is UserCreationError.EmailAlreadyInUse -> UserProblem.UserAlreadyExists(res.value).toResponseEntity()
                is UserCreationError.InvalidEmail -> UserProblem.InvalidEmail(res.value).toResponseEntity()
            }
        }
    }

    @GetMapping(Uris.Users.BASE)
    fun getUsers(@RequestParam role: String?, paginationInputs: PaginationInputs): ResponseEntity<*> {
        val usersCollection = service.getUsers(role, paginationInputs.skip, paginationInputs.limit)
        return usersCollection.toSirenObject(
            links = Uris.Pagination.getPaginationSirenLinks(
                uri = URI(Uris.Users.BASE),
                skip = paginationInputs.skip,
                limit = paginationInputs.limit,
                total = usersCollection.total,
            )
        ).toResponseEntity(200)
    }

    @GetMapping(Uris.Users.AUTHENTICATED_USER)
    fun getAuthenticatedUser(authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return UserDetailsOutputModel(
            id = authenticatedUser.user.id,
            name = authenticatedUser.user.name,
            email = authenticatedUser.user.email,
            avatarUrl = authenticatedUser.user.avatarUrl,
            role = authenticatedUser.user.role,
            createdAt = authenticatedUser.user.createdAt,
        ).toSirenObject(
            links = listOf(
                SirenLink.self(href = Uris.Users.buildUserByIdUri(authenticatedUser.user.id)),
                SirenLink(href = Uris.Stats.buildStatsByUserIdUri(authenticatedUser.user.id), rel = listOf("stats"))
            ),
            actions = listOf(
                getUpdateUserAction(),
                getDeleteUserAction(),
            )
        ).toResponseEntity(200)
    }

    @GetMapping(Uris.Users.USER_BY_ID)
    fun getUserById(@PathVariable id: Int): ResponseEntity<*> {
        return when (val res = service.getUserById(id)) {
            is Success -> res.value.toOutputModel().toSirenObject(
                links = listOf(
                    SirenLink.self(href = Uris.Users.buildUserByIdUri(res.value.id)),
                    SirenLink(href = Uris.Stats.buildStatsByUserIdUri(res.value.id), rel = listOf("stats"))
                )
            ).toResponseEntity(200)

            is Failure -> UserProblem.UserByIdNotFound(res.value).toResponseEntity()
        }
    }

    @PatchMapping(Uris.Users.BASE)
    fun updateUser(
        authenticatedUser: AuthenticatedUser,
        @RequestBody userInput: UserUpdateInputModel,
    ): ResponseEntity<*> {
        return when (val res = service.updateUser(authenticatedUser.user.id, userInput.name, userInput.avatarUrl)) {
            is Success -> Siren(
                properties = res.value,
                links = listOf(
                    SirenLink.self(href = Uris.Users.buildUserByIdUri(authenticatedUser.user.id)),
                    SirenLink(href = Uris.Stats.buildStatsByUserIdUri(authenticatedUser.user.id), rel = listOf("stats"))
                ),
                actions = listOf(
                    getDeleteUserAction(),
                )
            ).toResponseEntity(200)

            is Failure -> UserProblem.InvalidValues(res.value).toResponseEntity()
        }
    }

    @DeleteMapping(Uris.Users.BASE)
    fun deleteUser(authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (service.deleteUser(authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).body(
                Siren<Nothing>()
            )

            is Failure -> UserProblem.UserInAnOngoingMatch.toResponseEntity()
        }
    }

    @PutMapping(Uris.Users.TOKEN)
    fun createToken(@RequestBody input: UserCredentialsInputModel, response: HttpServletResponse): ResponseEntity<*> {
        return when (val res = service.createToken(input.email, input.password)) {
            is Success -> {
                response.addCookie(
                    Cookie(
                        AuthenticationDetails.NAME_AUTHORIZATION_COOKIE,
                        res.value.tokenValue
                    ).apply {
                        path = "/"
                        isHttpOnly = true
                    }
                )
                ResponseEntity.status(200).body(
                    Siren<Nothing>()
                )
            }

            is Failure -> UserProblem.InvalidCredentials(res.value).toResponseEntity()
        }
    }

    @DeleteMapping(Uris.Users.TOKEN)
    fun deleteToken(authenticatedUser: AuthenticatedUser, response: HttpServletResponse): ResponseEntity<*> {
        response.addCookie(
            Cookie(
                AuthenticationDetails.NAME_AUTHORIZATION_COOKIE,
                ""
            ).apply {
                path = "/"
                isHttpOnly = true
                maxAge = 3600
            }
        )
        service.deleteToken(authenticatedUser.token.tokenValue)
        return ResponseEntity.status(200).body(
            Siren<Nothing>()
        )
    }
}