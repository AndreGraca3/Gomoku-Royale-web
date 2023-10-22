package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.MatchState
import pt.isel.gomoku.domain.game.State
import pt.isel.gomoku.domain.game.Variant
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.cell.serialize
import pt.isel.gomoku.server.http.model.match.MatchCreationOutput
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.service.error.match.MatchCreationError
import pt.isel.gomoku.server.service.error.match.MatchError
import pt.isel.gomoku.server.service.error.match.MatchFetchingError
import pt.isel.gomoku.server.service.error.match.MatchJoiningError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success

@Component
class MatchService(private val trManager: TransactionManager) {

    fun createMatch(
        userId: Int,
        isPrivate: Boolean,
        size: Int?,
        variant: String?
    ): Either<MatchCreationError, MatchCreationOutput> {
        return trManager.run {
            // user is already in a match with another player,
            // or user is already waiting for another player
            when (it.matchRepository.isUserInMatch(userId)) {
                State.SETUP.name -> return@run failure(MatchCreationError.AlreadyInQueue(userId))
                State.ONGOING.name -> return@run failure(MatchCreationError.AlreadyInMatch(userId))
            }

            if (isPrivate && (variant == null || size == null))
                return@run failure(MatchCreationError.InvalidPrivateMatch(size, variant))

            // waiting for other player
            val newVariant = if (variant != null) Variant.from(variant) else Variant.getRandom()
            val newBoard = newVariant.createBoard(size)

            // there is a user with the same preferences as this user
            if (!isPrivate) {
                val match = it.matchRepository.getMatchByPreferences(size, variant)
                if (match != null) {
                    // add user to match
                    return@run success(
                        MatchCreationOutput(
                            it.matchRepository.updateMatch(
                                match.id,
                                whiteId = userId,
                                state = MatchState.ONGOING.name
                            )
                        )
                    )
                }
            }

            return@run success(
                MatchCreationOutput(
                    it.matchRepository.createMatch(
                        userId,
                        isPrivate,
                        newVariant.name,
                        newBoard.size,
                        newBoard::class.java.simpleName
                    )
                )
            )
        }
    }

    fun joinPrivateMatch(id: String, userId: Int): Either<MatchError, MatchCreationOutput> {
        return trManager.run {
            when (it.matchRepository.isUserInMatch(userId)) {
                State.SETUP.name -> return@run failure(MatchCreationError.AlreadyInQueue(userId))
                State.ONGOING.name -> return@run failure(MatchCreationError.AlreadyInMatch(userId))
            }

            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!match.isPrivate) return@run failure(MatchJoiningError.MatchIsNotPrivate(id))

            success(
                MatchCreationOutput(
                    it.matchRepository.updateMatch(
                        id,
                        whiteId = userId,
                        state = MatchState.ONGOING.name
                    )
                )
            )
        }
    }

    fun getMatchById(id: String, userId: Int): Either<MatchFetchingError, Match> {
        return trManager.run {
            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!isUserInMatch(userId, match))
                return@run failure(MatchFetchingError.UserNotInMatch(userId, match.id))

            success(match)
        }
    }

    fun getMatchesFromUser(idUser: Int): Either<Unit, List<Match>> {
        return trManager.run {
            success(it.matchRepository.getMatchesFromUser(idUser))
        }
    }

    fun play(userId: Int, id: String, dot: Dot): Either<MatchFetchingError, Stone> {
        return trManager.run {
            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!isUserInMatch(userId, match))
                return@run failure(MatchFetchingError.UserNotInMatch(userId, match.id))

            val player = match.getPlayer(userId)
            val newBoard = match.play(dot, player).board
            val serializedStones =
                newBoard.stones.serialize()

            it.boardRepository.updateBoard(
                id,
                newBoard::class.simpleName!!,
                serializedStones,
                newBoard.turn.symbol
            )

            success(
                Stone(player, dot)
            )
        }
    }

    fun deleteMatch(userId: Int): Either<Unit, Unit> {
        return trManager.run {
            success(it.matchRepository.deleteMatch(userId))
        }
    }

    private fun isUserInMatch(idUser: Int, match: Match) =
        match.blackId == idUser || match.whiteId == idUser
}
