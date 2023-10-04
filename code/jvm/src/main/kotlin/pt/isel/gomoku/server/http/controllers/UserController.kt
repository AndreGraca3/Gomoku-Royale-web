package pt.isel.gomoku.server.http.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.http.model.problem.UserProblem
import pt.isel.gomoku.server.http.model.user.UserCreateInputModel
import pt.isel.gomoku.server.http.model.user.UserCreateTokenInputModel
import pt.isel.gomoku.server.services.UserService
import pt.isel.gomoku.server.http.model.user.UserOut
import pt.isel.gomoku.server.pipeline.authorization.Auth
import pt.isel.gomoku.server.services.error.UserCreationError
import pt.isel.gomoku.server.utils.Failure
import pt.isel.gomoku.server.utils.Success

@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody input: UserCreateInputModel): ResponseEntity<*> {
        return when (val res = service.createUser(input.name, input.email, input.password, input.avatar)) {
            is Success -> ResponseEntity.status(201).body(res.value)
            is Failure -> when (res.value) {
                is UserCreationError.InsecurePassword -> UserProblem.InsecurePassword(res.value).response(400)
                is UserCreationError.UserAlreadyExists -> UserProblem.UserAlreadyExists(res.value).response(409)
            }
        }
    }

    @Auth
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserOut? = service.getUser(id)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody name: String, @RequestBody avatar: String) =
        service.updateUser(id, name, avatar)

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    fun createToken(@RequestBody input: UserCreateTokenInputModel) = service.createToken(input.username, input.password)
}