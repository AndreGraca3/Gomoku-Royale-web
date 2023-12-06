package pt.isel.gomoku.server.http.response.problem

import pt.isel.gomoku.server.service.errors.user.*

sealed class UserProblem(
    status: Int,
    subType: String,
    title: String,
    detail: String,
    data: Any? = null
) : Problem(subType, status, title, detail, data) {

    class UserAlreadyExists(data: UserCreationError.EmailAlreadyInUse) : UserProblem(
        409,
        "user-already-exists",
        "User already exists",
        "The email ${data.email} is already in use",
        data
    )

    class InsecurePassword(data: UserCreationError.InsecurePassword) : UserProblem(
        400,
        "insecure-password",
        "Insecure password",
        "The password ${data.password} must have at least 4 characters and one digit",
        data
    )

    class UserByIdNotFound(data: UserFetchingError.UserByIdNotFound) : UserProblem(
        404,
        "user-by-id-not-found",
        "User not found",
        "The user with id ${data.id} was not found",
        data
    )

    class InvalidCredentials(data: TokenCreationError.InvalidCredentials) : UserProblem(
        401,
        "invalid-credentials",
        "Invalid credentials",
        "Email or password are incorrect",
        data
    )

    class InvalidEmail(data: UserCreationError.InvalidEmail) : UserProblem(
        400,
        "invalid-email",
        "Invalid email",
        "The email ${data.email} is invalid",
        data
    )

    object InvalidToken : UserProblem(
        401,
        "invalid-token",
        "Invalid token",
        "Request's token is missing, invalid or expired"
    )

    class InvalidValues(data: UserUpdateError.InvalidValues) : UserProblem(
        400,
        "invalid-values",
        "Invalid values",
        "The values for user fields are invalid",
        data
    )

    object UserInAnOngoingMatch: UserProblem(
        403,
        "user-in-an-ongoing-match",
        "User in an ongoing match",
        "User can't be deleted until he finished current match",
    )
}