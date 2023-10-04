package pt.isel.gomoku.server.http.model.problem

sealed class BadRequestProblem(
    status: Int,
    subType: String,
    detail: String? = null,
    data: Any? = null
) : Problem(subType, status, "Bad Request", detail, data) {

    class InvalidRequestContent() : UserProblem(
        400,
        "invalid-request-content",
        "The request content is invalid"
    )
}