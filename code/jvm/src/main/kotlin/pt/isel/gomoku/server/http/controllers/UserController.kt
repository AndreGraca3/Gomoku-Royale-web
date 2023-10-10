package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.problem.UserProblem
import pt.isel.gomoku.server.http.model.user.*
import pt.isel.gomoku.server.services.UserService
import pt.isel.gomoku.server.services.error.user.UserCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success

@RestController
@RequestMapping(Uris.Users.BASE)
class UserController(private val service: UserService) {

    @PostMapping("")
    fun createUser(@RequestBody input: UserCreateInput): ResponseEntity<*> {
        return when (val res = service.createUser(input.name, input.email, input.password, input.avatarUrl)) {
            is Success -> ResponseEntity.status(201).body(res.value)
            is Failure -> when (res.value) {
                is UserCreationError.InsecurePassword -> UserProblem.InsecurePassword(res.value).response()
                is UserCreationError.EmailAlreadyInUse -> UserProblem.UserAlreadyExists(res.value).response()
            }
        }
    }

    @GetMapping(Uris.ID)
    fun getUser(@PathVariable id: Int): ResponseEntity<*> {
        return when (val res = service.getUserById(id)) {
            is Success -> ResponseEntity.status(200).body(res.value)
            is Failure -> UserProblem.UserByIdNotFound(res.value).response()
        }
    }

    @PutMapping("")
    fun updateUser(
        authenticatedUser: AuthenticatedUser,
        @RequestBody userInput: UserUpdateInput
    ): ResponseEntity<*> {
        return when (val res =
            service.updateUser(authenticatedUser.user.id, userInput.name, userInput.avatarUrl, userInput.roleChangeRequest)) {
            is Success -> ResponseEntity.status(200).build<Unit>()
            is Failure -> UserProblem.InvalidValues(res.value).response()
        }
    }

    @PostMapping(Uris.Users.TOKEN)
    fun createToken(@RequestBody input: UserCredentialsInput): ResponseEntity<*> {
        return when (val res = service.createToken(input.email, input.password)) {
            is Success ->
                ResponseEntity.status(200)
                    .body(UserTokenCreateOutput(res.value.tokenValue))

            is Failure -> UserProblem.InvalidCredentials(res.value).response()
        }
    }
}