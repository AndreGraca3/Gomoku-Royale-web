package pt.isel.gomoku.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.services.UserService

@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser() = service.addUser()
}