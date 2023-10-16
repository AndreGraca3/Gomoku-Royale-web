package pt.isel.gomoku.server.http.model.problem

import pt.isel.gomoku.server.service.error.match.MatchCreationError
import pt.isel.gomoku.server.service.error.match.MatchFetchingError

sealed class MatchProblem(
    status: Int,
    subType: String,
    title: String,
    detail: String,
    data: Any? = null
) : Problem(subType, status,title, detail, data) {

    class MatchNotFound(data: MatchFetchingError.MatchByIdNotFound) : MatchProblem(
        404,
        "board-id-not-found",
        "Match not found",
        "The match with id ${data.id} was not found",
        data
    )

    class UserNotInMatch(data: MatchFetchingError.UserNotInMatch): MatchProblem(
        403,
        "user-not-found-in-match",
        "User not found in match",
        "The user with id ${data.playerId} was not found in match with id ${data.matchId}",
        data
    )

    class InvalidVariant(data: MatchCreationError.InvalidVariant) : MatchProblem(
        400,
        "invalid-variant",
        "Invalid variant",
        "The variant ${data.variant} doesn't exist",
        data
    )

    class AlreadyInQueue(data: MatchCreationError.AlreadyInQueue): MatchProblem(
        409,
        "already-in-queue",
        "User already in queue",
        "User with id ${data.playerId} is already waiting in queue, exit to join another",
        data
    )
}
