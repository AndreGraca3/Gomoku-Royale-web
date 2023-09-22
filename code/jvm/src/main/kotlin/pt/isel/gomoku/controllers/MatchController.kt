package pt.isel.gomoku.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.services.MatchService

@RestController
@RequestMapping("/matches")
class MatchController(private val service: MatchService) {
}