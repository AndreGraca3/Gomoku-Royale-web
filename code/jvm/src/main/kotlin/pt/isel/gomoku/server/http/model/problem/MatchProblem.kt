package pt.isel.gomoku.server.http.model.problem

import pt.isel.gomoku.server.services.error.match.MatchCreationError
import pt.isel.gomoku.server.services.error.match.MatchFetchingError
import pt.isel.gomoku.server.services.error.match.MatchUpdateError

sealed class MatchProblem(
    status: Int,
    subType: String,
    detail: String,
    data: Any? = null
) : Problem(subType, status,"Match Problem", detail, data) {

    class InvalidMatchId(data: MatchFetchingError.MatchByIdNotFound) : MatchProblem(
        404,
        "board-id-not-found",
        "The board with id ${data.id} was not found in match",
        data
    )

    class InvalidPlayerInMatch(data: MatchCreationError.InvalidPlayerInMatch) : MatchProblem(
        404,
        "user-not-found-in-match",
        "The user with id ${data.playerId} was not found in match",
        data
    )

    class InvalidVariant(data: MatchCreationError.InvalidVariant) : MatchProblem(
        400,
        "invalid-variant",
        "The variant ${data.variant} is invalid",
        data
    )

    class InvalidValues(data: MatchUpdateError.InvalidValues) : MatchProblem(
        400,
        "invalid-values",
        "The values for match fields are invalid",
        data
    )
}
