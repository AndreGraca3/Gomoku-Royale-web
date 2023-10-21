package pt.isel.gomoku.server

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.match.MatchCreationInput

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GomokuMatchAPITests {

    @LocalServerPort
    var port: Int = 0

    /** tests are occurring in live DB !!!**/

    private val token = "Bearer 0Txy7bYpM9fZaEjKsLpQrVwXuT6jM0fD"

    @Test
    fun `create match returns created status code`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                token
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
    fun `create match returns bad request status code`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .header(
                "Authorization",
                token
            )
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `create match returns unauthorized status code`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `create match returns forbidden status code`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("id").isNotEmpty
    }


    @AfterEach
    fun rollBack() {

    }
}