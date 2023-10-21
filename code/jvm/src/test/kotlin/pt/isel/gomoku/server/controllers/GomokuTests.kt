package pt.isel.gomoku.server.controllers

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.event.annotation.AfterTestClass
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.model.user.UserCreateInput
import pt.isel.gomoku.server.http.model.user.UserIdOutput
import pt.isel.gomoku.server.http.model.user.UserInfo
import pt.isel.gomoku.server.http.model.user.UserUpdateInput
import kotlin.properties.Delegates


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GomokuTests {

    var idDummy by Delegates.notNull<Int>()

    @LocalServerPort
    var port: Int = 8080

    @BeforeTestClass
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
    }

    @AfterTestClass
    fun teardown() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        client.delete().uri("/users/$idDummy")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun updateUser(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.patch().uri("/users")
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

        val userInfo = client.post().uri("/users")
            .bodyValue(
                UserCreateInput(
                    "Dummy",
                    "dummy@gmail.com",
                    "dummy123",
                    null
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserIdOutput::class.java)

        client.delete().uri("/users/${userInfo.returnResult().responseBody!!.id}}")
            .exchange()
            .expectStatus().isOk

    }

    @Test
    fun getUser(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.get().uri("/users/${idDummy}")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserInfo::class.java)
    }

}