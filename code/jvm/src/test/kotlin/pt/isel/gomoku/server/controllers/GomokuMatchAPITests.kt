//package pt.isel.gomoku.server.controllers
//
//import org.junit.jupiter.api.AfterAll
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.server.LocalServerPort
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.web.reactive.server.WebTestClient
//import pt.isel.gomoku.server.http.Uris
//import pt.isel.gomoku.server.http.model.match.MatchCreationInput
//import pt.isel.gomoku.server.http.model.match.MatchCreationOutput
//import pt.isel.gomoku.server.http.model.problem.MatchProblem
//import pt.isel.gomoku.server.http.model.problem.Problem
//import pt.isel.gomoku.server.http.model.user.TokenCreateOutput
//import pt.isel.gomoku.server.http.model.user.UserCreateInput
//import pt.isel.gomoku.server.http.model.user.UserCredentialsInput
//import pt.isel.gomoku.server.http.model.user.UserIdOutput
//
//@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//class GomokuMatchAPITests {
//
//    @LocalServerPort
//    var port: Int = 8080
//
//    val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
//
//    @BeforeAll
//    fun setup() {
//        // create users
//        user1Id = client.post().uri(Uris.Users.BASE)
//            .bodyValue(
//                UserCreateInput(
//                    "Dummy",
//                    "dummy@gmail.com",
//                    "dummy123",
//                    null
//                )
//            ).exchange()
//            .expectStatus().isCreated
//            .expectBody(UserIdOutput::class.java)
//            .returnResult().responseBody!!.id
//
//        user2Id = client.post().uri(Uris.Users.BASE)
//            .bodyValue(
//                UserCreateInput(
//                    "Dummy",
//                    "dummy@gmail.com",
//                    "dummy123",
//                    null
//                )
//            )
//
//
//        // create token
//        token = "Bearer " + client.put().uri(Uris.Users.BASE + Uris.Users.TOKEN)
//            .bodyValue(
//                UserCredentialsInput(
//                "dummy@gmail.com",
//                "dummy123"
//            )
//            ).exchange()
//            .expectStatus().isCreated
//            .expectBody(TokenCreateOutput::class.java)
//            .returnResult().responseBody!!.token
//
//        // create match
//        matchId = client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                token
//            )
//            .bodyValue(
//                MatchCreationInput(
//                    false,
//                    15,
//                    "FreeStyle"
//                )
//            )
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody(MatchCreationOutput::class.java)
//            .returnResult().responseBody!!.id
//    }
//
//    @AfterAll
//    fun teardown() {
//        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
//        client.delete().uri("/users")
//            .header(
//                "Authorization",
//                token
//            )
//            .exchange()
//            .expectStatus().isNoContent
//    }
//
//
//    @Test
//    fun `create match returns created status code`() {
//        client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                token
//            )
//            .bodyValue(
//                MatchCreationInput(
//                    false,
//                    15,
//                    "FreeStyle"
//                )
//            )
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody()
//            .jsonPath("id").isNotEmpty
//    }
//
//    @Test
//    fun `create match returns bad request status code because body is missing`() {
//        client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                token
//            )
//            .exchange()
//            .expectStatus().isBadRequest
//    }
//
//    @Test
//    fun `create match returns unauthorized status code with no token in authorization header`() {
//        client.post().uri(Uris.Matches.BASE)
//            .exchange()
//            .expectStatus().isUnauthorized
//    }
//
//    @Test
//    fun `create match returns unauthorized status code with invalid token in authorization header`() {
//        client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                invalidToken
//            )
//            .exchange()
//            .expectStatus().isUnauthorized
//    }
//
//    @Test
//    fun `create match returns conflict status code when user is already in match`() {
//        client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                token
//            )
//            .bodyValue(
//                MatchCreationInput(
//                    false,
//                    15,
//                    "FreeStyle"
//                )
//            )
//            .exchange()
//            .expectStatus().is4xxClientError
//            .expectBody(MatchProblem::class.java)
//    }
//
//    @Test
//    fun `get match by id returns ok if match exists and user belongs to it`() {
//        client.get().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                token
//            )
//            .exchange()
//            .expectStatus().is4xxClientError
//            .expectBody(Problem::class.java)
//    }
//}