package pt.isel.gomoku.server.http.model.problem

import pt.isel.gomoku.server.service.error.user.TokenCreationError
import pt.isel.gomoku.server.service.error.user.UserCreationError
import pt.isel.gomoku.server.service.error.user.UserFetchingError
import pt.isel.gomoku.server.service.error.user.UserUpdateError

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

    class InvalidValues(data: UserUpdateError.InvalidValues) : UserProblem(
        400,
        "invalid-values",
        "Invalid values",
        "The values for user fields are invalid",
        data
    )
}