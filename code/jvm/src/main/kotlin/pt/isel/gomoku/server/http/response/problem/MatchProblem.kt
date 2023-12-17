package pt.isel.gomoku.server.http.response.problem

import pt.isel.gomoku.server.service.errors.match.MatchCreationError
import pt.isel.gomoku.server.service.errors.match.MatchFetchingError
import pt.isel.gomoku.server.service.errors.match.MatchJoiningError
import pt.isel.gomoku.server.service.errors.match.MatchPlayError

sealed class MatchProblem(
    status: Int,
    subType: String,
    title: String,
    detail: String,
    data: Any? = null
) : Problem(subType, status, title, detail, data) {

    class MatchNotFound(data: MatchFetchingError.MatchByIdNotFound) : MatchProblem(
        404,
        "board-id-not-found",
        "Match not found",
        "The match with id ${data.id} not found",
        data
    )

    class UserNotInMatch(data: MatchFetchingError.UserNotInMatch) : MatchProblem(
        403,
        "user-not-found-in-match",
        "User not found in match",
        "The user doesn't belong to this match",
        data
    )

    class InvalidVariant(data: MatchCreationError.InvalidVariant) : MatchProblem(
        400,
        "invalid-variant",
        "Invalid variant",
        "Invalid variant ${data.variant}",
        data
    )

    class InvalidBoardSize(data: MatchCreationError.InvalidBoardSize) : MatchProblem(
        400,
        "invalid-board-size",
        "Invalid board size",
        "Invalid board size for variant ${data.variant}, valid sizes are ${data.sizes}",
        data
    )


    class AlreadyInQueue(data: MatchCreationError.AlreadyInQueue) : MatchProblem(
        409,
        "already-in-queue",
        "User already in queue",
        "User is already waiting in queue, exit to join another",
        data
    )

    class AlreadyInMatch(data: MatchCreationError.UserAlreadyPlaying) : MatchProblem(
        409,
        "already-in-match",
        "User already in match",
        "User with id ${data.playerId} is already in a match",
        data
    )

    class InvalidPrivateMatch(data: MatchCreationError.InvalidPrivateMatch) : MatchProblem(
        400,
        "invalid-private-match",
        "Invalid private match values",
        "Size and variant must be specified in private matches",
        data
    )

    class AlreadyStarted(data: MatchJoiningError.AlreadyStarted) : MatchProblem(
        409,
        "match-already-started",
        "Match already started",
        "Match with id ${data.matchId} already started",
        data
    )

    class IsNotPrivate(data: MatchJoiningError.MatchIsNotPrivate) : MatchProblem(
        400,
        "match-is-not-private",
        "Match is not private",
        "Match with id ${data.matchId} is not private",
        data
    )

    class InvalidPlay(reason: String, data: MatchPlayError.InvalidPlay) : MatchProblem(
        400,
        "invalid-play",
        "Invalid play",
        reason,
        data
    )

    class InvalidTurn(reason: String, data: MatchPlayError.InvalidTurn) : MatchProblem(
        403,
        "invalid-turn",
        "Invalid turn",
        reason,
        data
    )

    class AlreadyFinished(reason: String) : MatchProblem(
        409,
        "match-finished",
        "Match already finished",
        reason,
    )

    class NotEnoughPlayers(data: MatchPlayError.NotStarted) : MatchProblem(
        403,
        "not-enough-players",
        "Match still waiting for players",
        "Match with id ${data.matchId} didn't started yet.",
    )
}
