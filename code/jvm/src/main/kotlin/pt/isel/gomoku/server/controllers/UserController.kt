package pt.isel.gomoku.server.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.services.UserService

@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser() = service.addUser()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String) = service.getUser(id)
}