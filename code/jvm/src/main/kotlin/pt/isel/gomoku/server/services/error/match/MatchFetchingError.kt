package pt.isel.gomoku.server.services.error.match

import java.util.UUID

sealed class MatchFetchingError {

    class MatchByIdNotFound(val id : UUID) : MatchFetchingError()
}