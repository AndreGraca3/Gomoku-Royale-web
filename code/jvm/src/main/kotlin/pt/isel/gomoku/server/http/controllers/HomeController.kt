package pt.isel.gomoku.server.http.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.HomeOutputModel
import pt.isel.gomoku.server.http.response.siren.SirenLink
import pt.isel.gomoku.server.http.response.siren.actions.MatchActions
import pt.isel.gomoku.server.http.response.siren.actions.UserActions
import java.lang.management.ManagementFactory
import java.net.URI
import java.time.OffsetDateTime

@RestController
class HomeController {

    private val runTimeBean = ManagementFactory.getRuntimeMXBean()

    @Autowired
    private val env: Environment? = null

    @GetMapping(Uris.API_BASE)
    fun home(): ResponseEntity<*> {
        return HomeOutputModel(
            name = "Gomoku Royale",
            description = "Gomoku Royale is a platform to play Gomoku online",
            version = env?.getProperty("app.version")?.toDouble() ?: 0.0,
            uptimeMs = runTimeBean.uptime,
            time = OffsetDateTime.now(),
            authors = listOf("André Graça", "Diogo Santos", "Daniel Caseiro")
        ).toSirenObject(
            actions = listOf(
                UserActions.getCreateUserAction(),
                UserActions.getCreateTokenAction(),
                UserActions.getDeleteTokenAction(),
                MatchActions.getCreatePublicMatchAction()
            ),
            links = listOf(
                SirenLink(listOf("authenticatedUser"), URI(Uris.Users.AUTHENTICATED_USER)),
                SirenLink(listOf("nonAuthenticatedUser"), URI("${Uris.Users.BASE}/:id")),
                SirenLink(listOf("token"), URI(Uris.Users.TOKEN)),
                SirenLink(listOf("user"), URI(Uris.Users.BASE)),
                SirenLink(listOf("leaderboard"), URI("${Uris.Stats.TOP}?skip=:skip&limit=:limit")),
                SirenLink(listOf("match"), URI("${Uris.Matches.BASE}/:id"))
            )
        ).toResponseEntity(200)
    }
}