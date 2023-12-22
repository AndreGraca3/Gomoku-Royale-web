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
//import pt.isel.gomoku.server.http.model.*
//
//@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//class GomokuMatchAPITests {
//
//    @LocalServerPort
//    var port: Int = 8080
//    var idDummy1 : Int = 0
//    var idToken1 : String = ""
//
//    var idDummy2 : Int = 0
//    var idToken2 : String = ""
//
//
//    val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
//
//    @BeforeAll
//    fun setup() {
//
//        // create users
//        val responseBody1 = client.post().uri(Uris.Users.BASE)
//            .bodyValue(
//                UserCreationInputModel(
//                    "Dummy1",
//                    "dummy1@gmail.com",
//                    "dummy123",
//                    null
//                )
//            ).exchange()
//            .expectStatus().isCreated
//            .expectBody(UserIdOutputModel::class.java)
//            .returnResult().responseBody
//
//        idDummy1 = responseBody1!!.id
//
//        val responseBody2 = client.post().uri(Uris.Users.BASE)
//            .bodyValue(
//                UserCreationInputModel(
//                    "Dummy2",
//                    "dummy1@gmail.com",
//                    "dummy123",
//                    null
//                )
//            ).exchange()
//            .expectStatus().isCreated
//            .expectBody(UserIdOutputModel::class.java)
//            .returnResult().responseBody
//
//        idDummy1 = responseBody2!!.id
//
//        // create tokens
//        val response1 = client.put().uri(Uris.Users.TOKEN)
//            .bodyValue(
//                UserCredentialsInput(
//                    "dummy1@gmail.com",
//                    "dummy123"
//                )
//            ).exchange()
//            .expectStatus().isOk
//            .expectCookie().exists("Authorization")
//            .expectBody(TokenCreationOutput::class.java)
//            .returnResult()
//
//        idToken1 = response1.responseCookies["Authorization"]!!.first()!!.value
//
//        val response2 = client.put().uri(Uris.Users.TOKEN)
//            .bodyValue(
//                UserCredentialsInput(
//                    "dummy1@gmail.com",
//                    "dummy123"
//                )
//            ).exchange()
//            .expectStatus().isOk
//            .expectCookie().exists("Authorization")
//            .expectBody(TokenCreationOutput::class.java)
//            .returnResult()
//
//        idToken2 = response2.responseCookies["Authorization"]!!.first()!!.value
//
//        // create match
//        matchId = client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                idToken1
//            )
//            .bodyValue(
//                MatchCreationInputModel(
//                    false,
//                    15,
//                    "FreeStyle"
//                )
//            )
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody(MatchOutputModel::class.java)
//            .returnResult().responseBody!!.id
//    }
//
//    @AfterAll
//    fun teardown() {
//        val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
//
//        // delete user1
//        client.delete().uri(Uris.Users.BASE)
//            .header(
//                "Authorization",
//                idToken1
//            )
//            .exchange()
//            .expectStatus().isOk
//
//        // delete user2
//        client.delete().uri(Uris.Users.BASE)
//            .header(
//                "Authorization",
//                idToken2
//            )
//            .exchange()
//            .expectStatus().isOk
//
//        // delete match
//        client.delete().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                idToken1
//            )
//            .exchange()
//            .expectStatus().isOk
//    }
//
//
//    @Test
//    fun `create match returns created status code and gets deleted`() {
//        client.post().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                idToken2
//            )
//            .bodyValue(
//                MatchCreationInputModel(
//                    false,
//                    15,
//                    "FreeStyle"
//                )
//            )
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody()
//            .jsonPath("id").isNotEmpty
//
//        client.delete().uri(Uris.Matches.BASE)
//            .header(
//                "Authorization",
//                idToken2
//            )
//            .exchange()
//            .expectStatus().isOk
//    }
//
////    @Test
////    fun `create match returns bad request status code because body is missing`() {
////        client.post().uri(Uris.Matches.BASE)
////            .header(
////                "Authorization",
////                token
////            )
////            .exchange()
////            .expectStatus().isBadRequest
////    }
////
////    @Test
////    fun `create match returns unauthorized status code with no token in authorization header`() {
////        client.post().uri(Uris.Matches.BASE)
////            .exchange()
////            .expectStatus().isUnauthorized
////    }
////
////    @Test
////    fun `create match returns unauthorized status code with invalid token in authorization header`() {
////        client.post().uri(Uris.Matches.BASE)
////            .header(
////                "Authorization",
////                invalidToken
////            )
////            .exchange()
////            .expectStatus().isUnauthorized
////    }
////
////    @Test
////    fun `create match returns conflict status code when user is already in match`() {
////        client.post().uri(Uris.Matches.BASE)
////            .header(
////                "Authorization",
////                token
////            )
////            .bodyValue(
////                MatchCreationInput(
////                    false,
////                    15,
////                    "FreeStyle"
////                )
////            )
////            .exchange()
////            .expectStatus().is4xxClientError
////            .expectBody(MatchProblem::class.java)
////    }
////
////    @Test
////    fun `get match by id returns ok if match exists and user belongs to it`() {
////        client.get().uri(Uris.Matches.BASE)
////            .header(
////                "Authorization",
////                token
////            )
////            .exchange()
////            .expectStatus().is4xxClientError
////            .expectBody(Problem::class.java)
////    }
//}