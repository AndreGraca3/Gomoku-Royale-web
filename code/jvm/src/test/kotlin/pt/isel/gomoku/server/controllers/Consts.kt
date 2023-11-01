package pt.isel.gomoku.server.controllers

/** USERS **/
object user1 {
    var id = 0
    const val NAME = "Diogo"
    const val EMAIL = "Diogo@gmail.com"
    const val PASSWORD = "pass123"

}

var user2Id = 0

/** TOKEN **/
var token = ""
const val invalidToken: String = "Invalid token"


/** MATCH **/
var matchId = ""
const val invalidMatchId: String = "Invalid id"

const val isPrivate = false
const val size = 15
const val variant = "FreeStyleBoard"
