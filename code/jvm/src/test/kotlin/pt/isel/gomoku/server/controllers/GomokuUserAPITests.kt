package pt.isel.gomoku.server.controllers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.event.annotation.AfterTestClass
import org.springframework.test.context.event.annotation.AfterTestExecution
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.model.user.*


@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GomokuUserAPITests {

    var idDummy : Int = 0
    var idToken : String = ""

    @LocalServerPort
    var port: Int = 8080

    @BeforeAll
    fun setup() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        idDummy = client.post().uri("/users")
            .bodyValue(
                UserCreateInput(
                    "Dummy",
                    "dummy@gmail.com",
                    "dummy123",
                    null
                )
            ).exchange()
            .expectStatus().isCreated
            .expectBody(UserIdOutput::class.java)
            .returnResult().responseBody!!.id

        idToken = client.put().uri("/users/token")
            .bodyValue(UserCredentialsInput(
                "dummy@gmail.com",
                "dummy123"
            )).exchange()
            .expectStatus().isCreated
            .expectBody(TokenCreateOutput::class.java)
            .returnResult().responseBody!!.token
    }

    @AfterAll
    fun teardown() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        client.delete().uri("/users")
            .header("Authorization", "Bearer $idToken")
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun updateUser(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.patch().uri("/users")
            .header("Authorization", "Bearer $idToken")
            .bodyValue(
                UserUpdateInput(
                    "DummyChanged",
                    null
                )
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(UserInfo::class.java)
    }

    @Test
    fun createDeleteUser() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val userInternal = client.post().uri("/users")
            .bodyValue(
                UserCreateInput(
                    "Dummy1",
                    "dummy1@gmail.com",
                    "dummy123",
                    null
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserIdOutput::class.java)

        val idTokenInternal = client.put().uri("/users/token")
            .bodyValue(UserCredentialsInput(
                "dummy1@gmail.com",
                "dummy123"
            )).exchange()
            .expectStatus().isCreated
            .expectBody(TokenCreateOutput::class.java)
            .returnResult().responseBody!!.token

        client.delete().uri("/users")
            .header("Authorization", "Bearer $idTokenInternal"
            ).exchange()
            .expectStatus().isNoContent

    }

    @Test
    fun getUser(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.get().uri("/users/${idDummy}")
            .header("Authorization", "Bearer $idToken"
            ).exchange()
            .expectStatus().isOk
            .expectBody(UserInfo::class.java)
    }
}