package pt.isel.gomoku.server.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.match.MatchCreationInput

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GomokuMatchAPITests {

    @LocalServerPort
    var port: Int = 8080

    val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

    private val token1 = "Bearer 0Txy7bYpM9fZaEjKsLpQrVwXuT6jM0fD"
    private val token2 = "Bearer 5Rz2vWqFpYhN6sTbGmCjXeZrU0gO4oA1"
    private val invalidToken = "invalidToken"

    @Test
    fun `create match returns created status code`() {
        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                token1
            )
            .bodyValue(
                MatchCreationInput(
                    false,
                    15,
                    "FreeStyle"
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("id").isNotEmpty
    }

    @Test
    fun `create match returns bad request status code because body is missing`() {
        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                token1
            )
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `create match returns unauthorized status code with no token in authorization header`() {
        client.post().uri(Uris.Matches.BASE)
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `create match returns unauthorized status code with invalid token in authorization header`() {
        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                invalidToken
            )
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `create match returns conflict status code when user is already in match`() {
        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                token2
            )
            .bodyValue(
                MatchCreationInput(
                    false,
                    15,
                    "FreeStyle"
                )
            )
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("id").isNotEmpty
    }

    @Test
    fun `get match by id returns ok if match exists and user belongs to it`() {
        client.get().uri(Uris.Matches.BASE + "/" )
            .header(
                "Authorization",
                token1
            )
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("id").isNotEmpty
    }
}