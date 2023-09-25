package pt.isel.gomoku.server

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class GomokuTests {

    @LocalServerPort
    var port: Int = 8080

    @Test
    fun createUserOK() {
        TODO()
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        client.post().uri("/users")
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("username").isEqualTo("filipe")
    }
}