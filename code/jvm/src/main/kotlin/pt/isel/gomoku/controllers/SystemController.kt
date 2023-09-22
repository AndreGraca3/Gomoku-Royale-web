package pt.isel.gomoku.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/system")
class SystemController {

    @GetMapping("/version")
    fun getSystemVersion() = 1.0

    @GetMapping("/authors")
    fun systemAuthors() = arrayOf("André Graça", "Diogo Santos", "Daniel Caseiro")
}