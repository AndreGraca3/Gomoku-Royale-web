package pt.isel.gomoku.server.services

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.domain.game.Dot
import pt.isel.gomoku.domain.game.Player
import pt.isel.gomoku.domain.game.boards.Board
import pt.isel.gomoku.server.http.model.match.MatchCreationOut
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
        userId: Int,
        isPrivate: Boolean,
        size: Int?,
        variant: String?
    ): Either<MatchCreationError, MatchCreationOut> {
        return trManager.run {
            val matchRepository = it.matchRepository
            val queueRepository = it.queueRepository

            val queue = queueRepository.getQueueByPreferences(isPrivate, size, variant)

            if (queue == null) {
                success(MatchCreationOut(queueRepository.createQueue(userId, isPrivate, size, variant)))
            } else {
                if (queue.playerId == userId) {
                    failure(MatchCreationError.AlreadyInQueue(queue.playerId))
                } else {
                    val newSize = size ?: Board.getRandomSize()
                    val serializedBoard = if (variant == null)
                        Board.getRandomBoard(newSize).serialize() else "$variant\n$newSize\n${Player.BLACK.symbol}"
                    queueRepository.removeFromQueue(queue.matchId)
                    success(
                        matchRepository.createMatch(
                            queue.matchId, isPrivate, serializedBoard, userId, queue.playerId
                        )
                    )
                }
            }
        }
    }

    fun getMatchById(id: UUID, userId: Int): Either<MatchFetchingError, Match> {
        return trManager.run {
            val match =
                it.matchRepository.getMatchById(id) ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))
            if (!checkIfUserInMatch(userId, match)) return@run failure(MatchFetchingError.UserNotInMatch(userId))
            success(match)
        }
    }

    fun getMatchesFromUser(idUser: Int): Either<Unit, List<Match>> {
        return trManager.run {
            success(it.matchRepository.getMatchesFromUser(idUser))
        }
    }

    fun updateMatch(
        id: UUID,
        winner: Int?,
        userId: Int
    ): Either<MatchUpdateError.InvalidValues, Unit> {
        return trManager.run {
            getMatchById(id, userId)
            success(
                it.matchRepository.updateMatch(
                    id,
                    winner
                )
            )
        }
    }

    private fun checkIfUserInMatch(idUser: Int, match: Match): Boolean {
        return match.player_black == idUser || match.player_white == idUser
    }

    fun playMove(
        userId: Int,
        id: UUID,
        move: Dot
    ): Either<MatchFetchingError, Unit> {
        return trManager.run {
            val match =
                it.matchRepository.getMatchById(id) ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!checkIfUserInMatch(userId, match)) {
                failure(MatchFetchingError.UserNotInMatch(userId))
            } else {
                val matchRepository = it.matchRepository
                val player = match.getPlayer(userId)
                val newBoard = match.board.play(move, player).serialize()
                success(
                    matchRepository.playMove(
                        id,
                        newBoard
                    )
                )
            }
        }
    }
}