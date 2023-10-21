package pt.isel.gomoku.server.http

object Uris {

    const val ID = "/{id}"

    object Users {
        const val BASE = "/users"
        const val TOKEN = "/token"
    }

    object Matches {
        const val BASE = "/matches"
        const val JOIN = "/join"
    }

    object Stats {
        const val BASE = "/stats"
        const val TOP = "/top"
    }
}