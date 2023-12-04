package pt.isel.gomoku.server.http.response.problem

sealed class ServerProblem(
    status: Int,
    subType: String,
    detail: String? = null,
    data: Any? = null,
) : Problem(subType, status, "Internal Server Error", detail, data) {

    class InternalServerError() : ServerProblem(
        500,
        "internal-server-error",
        "Something went wrong",
        null
    )
}