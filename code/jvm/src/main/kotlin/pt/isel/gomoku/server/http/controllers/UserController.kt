package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.problem.UserProblem
import pt.isel.gomoku.server.http.model.user.UserCreateInputModel
import pt.isel.gomoku.server.http.model.user.UserCredentialsInputModel
import pt.isel.gomoku.server.http.model.user.UserTokenCreateOutputModel
import pt.isel.gomoku.server.services.UserService
import pt.isel.gomoku.server.services.error.user.UserCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success

@RestController
@RequestMapping(Uris.Users.BASE)
class UserController(private val service: UserService) {

    @PostMapping("")
    fun createUser(@RequestBody input: UserCreateInputModel): ResponseEntity<*> {
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
            is Success -> ResponseEntity.status(201).body(res.value)
            is Failure -> UserProblem.UserByIdNotFound(res.value).response()
        }
    }

    @PutMapping(Uris.ID)
    fun updateUser(@PathVariable id: Int, @RequestBody name: String, @RequestBody avatarUrl: String) {
        service.updateUser(id, name, avatarUrl)
    }

    @PostMapping(Uris.Users.TOKEN)
    fun createToken(@RequestBody input: UserCredentialsInputModel): ResponseEntity<*> {
        return when (val res = service.createToken(input.email, input.password)) {
            is Success ->
                ResponseEntity.status(200)
                    .body(UserTokenCreateOutputModel(res.value.tokenValue))

            is Failure -> UserProblem.InvalidCredentials(res.value).response()
        }
    }
}