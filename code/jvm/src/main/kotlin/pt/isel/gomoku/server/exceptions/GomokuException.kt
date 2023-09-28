package pt.isel.gomoku.server.exceptions

import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CONFLICT
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.NO_CONTENT
import org.http4k.core.Status.Companion.UNAUTHORIZED

// TODO: Came from LS project, need to change to Gomoku
// Check the HTTP4k library
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
