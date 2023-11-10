package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.problem.UserProblem
import pt.isel.gomoku.server.http.model.siren.*
import pt.isel.gomoku.server.http.model.siren.actions.UserActions
import pt.isel.gomoku.server.http.model.siren.actions.UserActions.CREATE_TOKEN_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.UserActions.CREATE_USER_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.UserActions.DELETE_USER_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.UserActions.GET_USER_ACTION
import pt.isel.gomoku.server.http.model.siren.actions.UserActions.UPDATE_USER_ACTION
import pt.isel.gomoku.server.http.model.user.*
import pt.isel.gomoku.server.service.UserService
import pt.isel.gomoku.server.service.error.user.UserCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success

@RestController
@RequestMapping(Uris.Users.BASE)
class UserController(private val service: UserService) {

    @PostMapping()
    fun createUser(@RequestBody input: UserCreateInput): ResponseEntity<*> {
        return when (val res = service.createUser(input.name, input.email, input.password, input.avatarUrl)) {
            is Success -> ResponseEntity.status(201).body(
                SirenEntity(
                    clazz = listOf("user"),
                    properties = res.value,
                    entities = listOf(
                        EmbeddedLink(
                            rel = listOf("stats"),
                            href = "${Uris.Stats.BASE}${Uris.Users.BASE}${res.value.id}"
                        )
                    ),
                    links = listOf(
                        SirenLink.self(href = "${Uris.Users.BASE}/${res.value.id}")
                    ),
                    actions = listOf(
                        GET_USER_ACTION,
                        UPDATE_USER_ACTION,
                        DELETE_USER_ACTION,
                        CREATE_TOKEN_ACTION
                    )
                )
            )

            is Failure -> when (res.value) {
                is UserCreationError.InsecurePassword -> UserProblem.InsecurePassword(res.value).response()
                is UserCreationError.EmailAlreadyInUse -> UserProblem.UserAlreadyExists(res.value).response()
                is UserCreationError.InvalidEmail -> UserProblem.InvalidEmail(res.value).response()
            }
        }
    }

    @GetMapping(Uris.ID)
    fun getUser(@PathVariable id: Int): ResponseEntity<*> {
        return when (val res = service.getUserById(id)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("user"),
                    properties = res.value,
                    entities = listOf(
                        EmbeddedLink(
                            rel = listOf("stats"),
                            href = "${Uris.Stats.BASE}${Uris.Users.BASE}${res.value.id}"
                        )
                    ),
                    links = listOf(
                        SirenLink.self(
                            href = Uris.Users.BASE + Uris.ID
                        )
                    ),
                    actions = listOf(
                        UPDATE_USER_ACTION,
                        DELETE_USER_ACTION,
                        CREATE_TOKEN_ACTION
                    )
                )
            )

            is Failure -> UserProblem.UserByIdNotFound(res.value).response()
        }
    }

    @PatchMapping()
    fun updateUser(
        authenticatedUser: AuthenticatedUser,
        @RequestBody userInput: UserUpdateInput
    ): ResponseEntity<*> {
        return when (val res =
            service.updateUser(authenticatedUser.user.id, userInput.name, userInput.avatarUrl)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("user"),
                    properties = res.value,
                    entities = listOf(
                        EmbeddedLink(
                            rel = listOf("stats"),
                            href = "${Uris.Stats.BASE}${Uris.Users.BASE}${res.value.id}"
                        )
                    ),
                    links = listOf(
                        SirenLink.self(href = "${Uris.Users.BASE}/${res.value.id}")
                    ),
                    actions = listOf(
                        GET_USER_ACTION,
                        DELETE_USER_ACTION,
                        CREATE_TOKEN_ACTION
                    )
                )
            )

            is Failure -> UserProblem.InvalidValues(res.value).response()
        }
    }

    @DeleteMapping()
    fun deleteUser(authenticatedUser: AuthenticatedUser): ResponseEntity<*> {
        return when (val res = service.deleteUser(authenticatedUser.user.id)) {
            is Success -> ResponseEntity.status(200).body(
                SirenEntity(
                    clazz = listOf("user"),
                    properties = null,
                    actions = listOf(
                        CREATE_USER_ACTION
                    )
                )
            )

            is Failure -> UserProblem.UserByIdNotFound(res.value).response()
        }
    }

    @PutMapping(Uris.Users.TOKEN)
    fun createToken(@RequestBody input: UserCredentialsInput): ResponseEntity<*> {
        return when (val res = service.createToken(input.email, input.password)) {
            is Success -> ResponseEntity.status(201).body(
                SirenEntity(
                    clazz = listOf("user"),
                    properties = res.value,
                    entities = listOf(
                        EmbeddedLink(
                            rel = listOf("stats"),
                            href = "${Uris.Stats.BASE}${Uris.Users.BASE}/${res.value.userId}"
                        )
                    ),
                    links = listOf(
                        SirenLink.self(
                            href = "${Uris.Users.BASE}/${res.value.userId}"
                        )
                    ),
                    actions = listOf(
                        GET_USER_ACTION,
                        UPDATE_USER_ACTION,
                        DELETE_USER_ACTION
                    )
                )
            )

            is Failure -> UserProblem.InvalidCredentials(res.value).response()
        }
    }
}