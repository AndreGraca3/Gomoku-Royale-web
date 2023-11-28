package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.server.http.model.system.SystemDomain
import pt.isel.gomoku.server.http.model.system.SystemInfo
import pt.isel.gomoku.server.http.model.siren.SirenEntity
import pt.isel.gomoku.server.service.UserService

@RestController
@RequestMapping("/system")
class SystemController(val systemDomain: SystemDomain, val userService: UserService) {

    @GetMapping()
    fun getSystemInfo() =
        ResponseEntity.status(200).body(
            SirenEntity(
                clazz = listOf("system"),
                properties = SystemInfo(systemDomain.version, userService.getUsers("admin")),
            )
        )
}