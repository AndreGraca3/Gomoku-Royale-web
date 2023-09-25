package pt.isel.gomoku.server.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.server.services.MatchService

@RestController
@RequestMapping("/matches")
class MatchController(private val service: MatchService) {
}