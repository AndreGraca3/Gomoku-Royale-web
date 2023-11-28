package pt.isel.gomoku.server.pipeline

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.gomoku.domain.exception.GomokuGameException
import pt.isel.gomoku.server.http.model.problem.BadRequestProblem
import pt.isel.gomoku.server.http.model.problem.MatchProblem
import pt.isel.gomoku.server.service.error.match.MatchCreationError
import pt.isel.gomoku.server.service.error.match.MatchPlayError

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.info("Handling MethodArgumentNotValidException: {}", ex.message)
        return BadRequestProblem.InvalidMethod().response()
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.info("Handling HttpMessageNotReadableException: {}", ex.message)
        return BadRequestProblem.InvalidRequestContent().response()
    }

    @ExceptionHandler(GomokuGameException::class)
    fun handleGomokuGameException(ex: GomokuGameException): ResponseEntity<Any> {
        log.info("Handling GomokuGameException: {}", ex.message)
        return when (ex) {
            is GomokuGameException.InvalidPlay -> MatchProblem.InvalidPlay(
                ex.message,
                MatchPlayError.InvalidPlay(ex.dst)
            ).response()

            is GomokuGameException.InvalidBoardSize -> MatchProblem.InvalidBoardSize(
                MatchCreationError.InvalidBoardSize(ex.variant, ex.size, ex.sizes)
            ).response()

            is GomokuGameException.InvalidVariant -> MatchProblem.InvalidVariant(
                MatchCreationError.InvalidVariant(ex.variant)
            ).response()

            is GomokuGameException.NotEnoughPlayers -> MatchProblem.NotEnoughPlayers(
                MatchPlayError.NotStarted(ex.matchId)
            ).response()
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(CustomExceptionHandler::class.java)
    }
}