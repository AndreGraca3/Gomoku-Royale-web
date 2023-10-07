package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.http.model.match.MatchCreateInputModel
import pt.isel.gomoku.server.http.model.match.MatchCreationOut
import pt.isel.gomoku.server.http.model.match.MatchOut
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.services.error.match.MatchCreationError
import pt.isel.gomoku.server.services.error.match.MatchFetchingError
import pt.isel.gomoku.server.services.error.match.MatchUpdateError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success
import java.util.*

@Component
class MatchService(private val trManager: TransactionManager) {

    fun createMatch(input: MatchCreateInputModel): Either<MatchCreationError, MatchCreationOut> {

        // validate input ??
        return trManager.run {
            val userRepository = it.userRepository
            val matchRepository = it.matchRepository

            if (userRepository.getUserById(input.player1_id) == null)
                failure(MatchCreationError.InvalidPlayerInMatch(playerId = input.player1_id))

            // TODO() -> Implementar a Queue

            val id = matchRepository.createMatch(
                input.visibility,
                input.boardSpecs.toString(),
                input.variant,
                input.player1_id
            )
            success(id)
        }
    }

    fun getMatchesFromUser(idUser: Int) {
        trManager.run {
            //TODO()
        }
    }

    fun getMatchById(id: UUID): Either<MatchFetchingError.MatchByIdNotFound, MatchOut> {
        return trManager.run {
            val match = it.matchRepository.getMatchById(id)
            if (match != null) success(match)
            else failure(MatchFetchingError.MatchByIdNotFound(id))
        }
    }

    fun updateMatch(
        id: UUID,
        newVisibility: String,
        newWinner: Int
    ): Either<MatchUpdateError.InvalidValues, Unit> {
        if(newVisibility?.isBlank() == true && newWinner == null){

        }
    }
}