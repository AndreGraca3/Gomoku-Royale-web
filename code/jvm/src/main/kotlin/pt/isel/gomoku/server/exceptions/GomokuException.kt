package pt.isel.gomoku.server.exceptions



// TODO: Came from LS project, need to change to Gomoku
/*
sealed class GomokuException(message: String, val status: Status){
    class NotAuthorized : GomokuException("Unauthorized Operation.", UNAUTHORIZED)

    class NotFound(msg: String) : GomokuException(msg, NOT_FOUND)

    class IllegalArgument(msg: String) : GomokuException(msg, BAD_REQUEST)

    class AlreadyExists(msg: String) : GomokuException(msg, CONFLICT)

    class NoContent() : GomokuException("", NO_CONTENT)

    class InternalError() : GomokuException("Server Error", INTERNAL_SERVER_ERROR)
}

val map: Map<String, (String) -> GomokuException> = mapOf(
    "23505" to { str -> GomokuException.AlreadyExists(str) },
    "22001" to { str -> GomokuException.IllegalArgument(str) }
)

const val NOT_FOUND = "not found."

const val ALREADY_EXISTS = "already exists."

const val INVAL_PARAM = "Invalid parameter:"
 */