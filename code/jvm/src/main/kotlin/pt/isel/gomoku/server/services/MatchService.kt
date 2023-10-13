package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.server.http.model.match.MatchCreationOut
import pt.isel.gomoku.server.http.model.match.MatchOutDev
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

    fun createMatch(
        playerId: Int,
        isPrivate: Boolean,
        size: Int,
        variant: String
    ): Either<MatchCreationError, MatchCreationOut> {
        return trManager.run {
            val userRepository = it.userRepository
            val matchRepository = it.matchRepository
            val queueRepository = it.queueRepository

            if (userRepository.getUserById(playerId) == null)
                failure(MatchCreationError.InvalidPlayerInMatch(playerId = playerId))
            // else??
            // retornar failure se tentar adicionar a queue outra vez.
            // colocar a null para identificar o utilizador quer jogar um jogo qualquer
            val queue = queueRepository.getQueueByPreferences(isPrivate, size, variant)
            if (queue != null && queue.playerId != playerId) {
                val serializedBoard = "$variant\n$size\n${Player.BLACK.symbol}\n[]\n"
                queueRepository.removeFromQueue(queue.matchId)
                success(
                    matchRepository.createMatch(
                        queue.matchId, isPrivate, serializedBoard, playerId, queue.playerId
                    )
                )
            } else {
                success(MatchCreationOut(queueRepository.createQueue(playerId, isPrivate, size, variant)))
            }
        }
    }

    fun getMatchesFromUser(idUser: Int) {
        trManager.run {
            TODO("Not yet implemented")
        }
    }

    fun getMatchById(id: UUID, userId: Int): Either<MatchFetchingError.MatchByIdNotFound, MatchOutDev> {
        return trManager.run {
            val match = checkIfUserInMatch(userId, id)
            if (match != null) success(match)
            else failure(MatchFetchingError.MatchByIdNotFound(id))
        }
    }

    fun updateMatch(
        id: UUID,
        newVisibility: String?,
        newWinner: Int?,
        userId: Int
    ): Either<MatchUpdateError.InvalidValues, Unit> {

        // TODO() -> Check this condition, || or &&
        if (newVisibility?.isBlank() == true || newWinner == null)
            return failure(MatchUpdateError.InvalidValues)

        return trManager.run {
            if (checkIfUserInMatch(userId, id) == null)
                failure(MatchUpdateError.InvalidValues)
            success(
                it.matchRepository.updateMatch(
                    id,
                    newVisibility,
                    newWinner
                )
            )
        }
    }

    // Verifies if the user is in the match
    private fun checkIfUserInMatch(idUser: Int, idMatch: UUID): MatchOutDev? {
        return trManager.run {
            val match = it.matchRepository.getMatchDev(idMatch)
            if (match == null) null
            else if (match.player1Id != idUser && match.player2Id != idUser)
                null
            else match
        }
    }

//    fun playMove(
//        idUser: Int,
//        idMatch: UUID,
//        move: String
//    ): Either<MatchPlayError, Unit> {
//        return trManager.run {
//
//            // Check if match exists
//            val match = it.matchRepository.getMatchDev(idMatch)
//            if (match == null) failure(MatchPlayError.MatchNotStarted(idMatch))
//
//            val isPlayerBlack = true
//
//            // Check if auth player is present in the match
//            if (match.player1Id != idUser) {
//                failure(MatchPlayError.MatchNotStartedByPlayer(idUser))
//            } else if (match.player2Id != idUser) {
//                failure(MatchPlayError.MatchNotStartedByPlayer(idUser))
//            }
//
//            // Check if is the user turn
//            // TODO()
//
//
//        }
//    }


}