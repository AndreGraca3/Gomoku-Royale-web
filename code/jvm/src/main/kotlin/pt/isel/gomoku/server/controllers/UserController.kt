package pt.isel.gomoku.server.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.gomoku.server.services.UserService
import pt.isel.gomoku.server.structs.dto.outbound.UserIn
import pt.isel.gomoku.server.structs.dto.outbound.UserOUT

@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody userIn: UserIn): Int = service.addUser(userIn)

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserOUT? = service.getUser(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun changeName(@PathVariable id: Int, @RequestBody name: String) = service.changeName(id, name)
}