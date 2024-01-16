package pt.isel.gomoku.server.controllers

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.model.*
import pt.isel.gomoku.server.http.response.siren.Siren
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationDetails

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GomokuMatchAPITests {

    @LocalServerPort
    var port: Int = 8080

    var idDummy1: Int = 0
    var idToken1: String = ""

    var idDummy2: Int = 0
    var idToken2: String = ""

    @BeforeEach
    fun setup() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        // create users
        val responseBody1 = client.post().uri(Uris.Users.BASE)
            .bodyValue(
                UserCreationInputModel(
                    "Dummy1",
                    "dummy1@gmail.com",
                    "dummy123",
                    null
                )
            ).exchange()
            .expectBody(object : ParameterizedTypeReference<Siren<UserIdOutputModel>>() {})  // Expect a Siren response
            .returnResult().responseBody?.properties ?: throw IllegalStateException("User creation failed")

        idDummy1 = responseBody1.id

        val responseBody2 = client.post().uri(Uris.Users.BASE)
            .bodyValue(
                UserCreationInputModel(
                    "Dummy2",
                    "dummy2@gmail.com",
                    "dummy123",
                    null
                )
            ).exchange()
            .expectBody(object : ParameterizedTypeReference<Siren<UserIdOutputModel>>() {})  // Expect a Siren response
            .returnResult().responseBody?.properties ?: throw IllegalStateException("User creation failed")

        idDummy2 = responseBody2.id

        // create tokens
        val response1 = client.put().uri(Uris.Users.TOKEN)
            .bodyValue(
                UserCredentialsInputModel(
                    "dummy1@gmail.com",
                    "dummy123"
                )
            ).exchange()
            .expectCookie().exists("Authorization")
            .returnResult(String::class.java)

        idToken1 = response1.responseCookies["Authorization"]!!.first()!!.value

        val response2 = client.put().uri(Uris.Users.TOKEN)
            .bodyValue(
                UserCredentialsInputModel(
                    "dummy2@gmail.com",
                    "dummy123"
                )
            ).exchange()
            .expectCookie().exists(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE)
            .returnResult(String::class.java)

        idToken2 = response2.responseCookies[AuthenticationDetails.NAME_AUTHORIZATION_COOKIE]!!.first()!!.value

        // create match
        matchId = client.post().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .bodyValue(
                MatchCreationInputModel(
                    false,
                    15,
                    "FreeStyle"
                )
            )
            .exchange()
            .expectBody(object : ParameterizedTypeReference<Siren<MatchCreationOutputModel>>() {})
            .returnResult().responseBody?.properties?.id ?: throw IllegalStateException("Match creation failed")
    }

    @AfterEach
    fun teardown() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        // delete match
        client.delete().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .exchange()
            .expectStatus().isOk

        // delete user2
        client.delete().uri(Uris.Users.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken2)
            .exchange()
            .expectStatus().isOk

        // delete user1
        client.delete().uri(Uris.Users.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .exchange()
            .expectStatus().isOk
    }

    // cant test this because cant delete ongoing match
    /*
    @Test
    fun `create match returns created status code and gets deleted`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken2)
            .bodyValue(
                MatchCreationInputModel(
                    false,
                    15,
                    "FreeStyle"
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody(object : ParameterizedTypeReference<Siren<MatchCreationOutputModel>>() {})
            .returnResult().responseBody?.properties?.id ?: throw IllegalStateException("Match creation failed")

        client.put().uri(Uris.Matches.buildForfeitMatchUri(matchId))
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken2)
            .exchange()
            .expectStatus().isOk

        client.delete().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken2)
            .exchange()
            .expectStatus().isOk
    }*/

    @Test
    fun `create match returns bad request status code because body is missing`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `create match returns unauthorized status code with no token in authorization cookie`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `create match returns unauthorized status code with invalid token in authorization cookie`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, invalidToken)
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `create match returns conflict status code when user is already in match`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.post().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .bodyValue(
                MatchCreationInputModel(
                    false,
                    15,
                    "FreeStyle"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(409)
    }

    @Test
    fun `get match by id returns ok if match exists and user belongs to it`() {
        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()

        client.get().uri(Uris.Matches.BASE)
            .cookie(AuthenticationDetails.NAME_AUTHORIZATION_COOKIE, idToken1)
            .exchange()
            .expectStatus().isOk
    }
}