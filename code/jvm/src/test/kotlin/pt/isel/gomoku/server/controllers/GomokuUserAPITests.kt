package pt.isel.gomoku.server.controllers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.*
import pt.isel.gomoku.server.http.response.siren.Siren
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationDetails


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

        val responseBody : Siren<UserIdOutputModel> = client.post().uri(Uris.Users.BASE)
            .bodyValue(
                UserCreationInputModel(
                    "Dummy",
                    "dummy@gmail.com",
                    "dummy123",
                    null
                )
            ).exchange()
            .expectStatus().isCreated
            .expectBody(Siren::class.java)  // Expect a Siren response
            .returnResult().responseBody?.properties as Siren<UserIdOutputModel>

        idDummy = responseBody.properties?.id!!
        
        val response = client.put().uri(Uris.Users.TOKEN)
            .bodyValue(
                UserCredentialsInput(
                    "dummy@gmail.com",
                    "dummy123"
                )
            ).exchange()
            .expectStatus().isOk
            .expectCookie().exists("Authorization")
            .expectBody(Void::class.java)
            .returnResult()

        idToken = response.responseCookies["Authorization"]!!.first()!!.value
    }


    @AfterAll
    fun teardown() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        client.delete().uri(Uris.Users.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun updateUser_OK(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.patch().uri(Uris.Users.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken)
            .bodyValue(
                UserUpdateInputModel(
                    "DummyChanged",
                    null
                )
            )
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun updateUser_Unauthorized(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.patch().uri(Uris.Users.BASE)
            .bodyValue(
                UserUpdateInputModel(
                    "DummyChanged",
                    null
                )
            )
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun createDeleteUser() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val userInternal = client.post().uri(Uris.Users.BASE)
            .bodyValue(
                UserCreationInputModel(
                    "Dummy1",
                    "dummy1@gmail.com",
                    "dummy123",
                    null
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserIdOutputModel::class.java)

        val idTokenInternal = client.put().uri(Uris.Users.TOKEN)
            .bodyValue(
                UserCredentialsInput(
                "dummy1@gmail.com",
                "dummy123"
            )
            ).exchange()
            .expectStatus().isOk
            .expectBody(Void::class.java)
            .returnResult().responseCookies["Authorization"]!!.first()!!.value

        client.delete().uri(Uris.Users.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idTokenInternal)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun createUser_BadRequest() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Users.BASE)
            .bodyValue(
                UserCreationInputModel(
                    "Dummy",
                    "invalid_email",
                    "dummy123",
                    null
                )
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun getUser_OK(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.get().uri("${Uris.Users.BASE}/${idDummy}")
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun getUser_NotFound(){
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        val user = client.get().uri("${Uris.Users.BASE}/9999")
            .exchange()
            .expectStatus().isNotFound
    }
}