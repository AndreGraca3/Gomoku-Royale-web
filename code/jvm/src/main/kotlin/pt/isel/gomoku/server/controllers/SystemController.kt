package pt.isel.gomoku.server.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.server.structs.dto.outbound.SystemDTO

@RestController
@RequestMapping("/system")
class SystemController {

    @GetMapping("")
    fun getSystemVersion() = SystemDTO(1.0F, listOf("André Graça", "Diogo Santos", "Daniel Caseiro"))
}