package pt.isel.gomoku.server.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.server.http.model.siren.SirenEntity
import pt.isel.gomoku.server.http.model.siren.SirenLink

@RestController
class HomeController {

    @GetMapping()
    fun home(): ResponseEntity<*> {
        return ResponseEntity.status(200).body(
            SirenEntity<Unit>(
                clazz = listOf("home"),
                links = listOf(
                    SirenLink.self(
                        href = ""
                    ),
                    SirenLink(
                        rel = listOf("users"),
                        href = "/users"
                    ),
                    SirenLink(
                        rel = listOf("matches"),
                        href = "/matches"
                    ),
                    SirenLink(
                        rel = listOf("stats"),
                        href = "/stats"
                    ),
                ),
            )
        )
    }
}