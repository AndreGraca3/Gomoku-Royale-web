package pt.isel.gomoku.server.pipeline

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.gomoku.domain.game.exception.GomokuGameException
import pt.isel.gomoku.server.http.response.problem.BadRequestProblem
import pt.isel.gomoku.server.http.response.problem.MatchProblem
import pt.isel.gomoku.server.service.errors.match.MatchCreationError
import pt.isel.gomoku.server.service.errors.match.MatchPlayError

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        return BadRequestProblem.InvalidMethod().toResponseEntity()
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        return BadRequestProblem.InvalidRequestContent().toResponseEntity()
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        log.info("Handling HttpMessageNotReadableException: {}", ex.message)
        return BadRequestProblem.InvalidRequestContent().toResponseEntity()
    }

    @ExceptionHandler(GomokuGameException::class)
    fun handleGomokuGameException(ex: GomokuGameException): ResponseEntity<Any> {
        log.info("Handling GomokuGameException: {}", ex.message)
        return when (ex) {
            is GomokuGameException.InvalidPlay -> MatchProblem.InvalidPlay(
                ex.message,
                MatchPlayError.InvalidPlay(ex.dst)
            ).toResponseEntity()

            is GomokuGameException.InvalidTurn -> MatchProblem.InvalidTurn(
                ex.message,
                MatchPlayError.InvalidTurn(ex.turn)
            ).toResponseEntity()

            is GomokuGameException.AlreadyFinished -> MatchProblem.AlreadyFinished(
                ex.message,
            ).toResponseEntity()

            is GomokuGameException.InvalidBoardSize -> MatchProblem.InvalidBoardSize(
                MatchCreationError.InvalidBoardSize(ex.variant, ex.size, ex.sizes)
            ).toResponseEntity()

            is GomokuGameException.InvalidVariant -> MatchProblem.InvalidVariant(
                MatchCreationError.InvalidVariant(ex.variant)
            ).toResponseEntity()

            is GomokuGameException.NotEnoughPlayers -> MatchProblem.NotEnoughPlayers(
                MatchPlayError.NotStarted(ex.matchId)
            ).toResponseEntity()
        }
    }

    /*@ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        log.info("Handling Internal Exception: {}", ex.message)
        return ServerProblem.InternalServerError().toResponseEntity()
    }*/

    companion object {
        private val log = LoggerFactory.getLogger(CustomExceptionHandler::class.java)
    }
}