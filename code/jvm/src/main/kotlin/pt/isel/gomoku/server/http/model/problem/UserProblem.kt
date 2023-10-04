package pt.isel.gomoku.server.http.model.problem

import pt.isel.gomoku.server.services.error.UserCreationError

sealed class UserProblem(
    subType: String,
    title: String? = null,
    detail: String? = null,
    data: Any? = null
) : Problem(subType, title, detail, data) {
    class UserAlreadyExists(data: UserCreationError.UserAlreadyExists) : UserProblem(
        "user-already-exists",
        "User already exists",
        "The email is already in use",
        data
    )

    class InsecurePassword(data: UserCreationError.InsecurePassword) : UserProblem(
        "insecure-password",
        "Insecure password",
        "The password must have at least 4 characters and one digit",
        data
    )
}