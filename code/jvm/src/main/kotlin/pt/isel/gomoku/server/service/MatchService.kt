package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.Match
import pt.isel.gomoku.domain.MatchState
import pt.isel.gomoku.domain.Variant
import pt.isel.gomoku.domain.game.board.BoardDraw
import pt.isel.gomoku.domain.game.board.BoardWinner
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.domain.game.cell.Stone
import pt.isel.gomoku.domain.game.cell.serialize
import pt.isel.gomoku.server.http.model.*
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.service.errors.match.MatchCreationError
import pt.isel.gomoku.server.service.errors.match.MatchError
import pt.isel.gomoku.server.service.errors.match.MatchFetchingError
import pt.isel.gomoku.server.service.errors.match.MatchJoiningError
import pt.isel.gomoku.server.utils.Either
import pt.isel.gomoku.server.utils.failure
import pt.isel.gomoku.server.utils.success

@Component
class MatchService(private val trManager: TransactionManager) {

    fun createMatch(
        userId: Int,
        isPrivate: Boolean,
        size: Int?,
        variant: String?,
    ): Either<MatchCreationError, MatchCreationOutputModel> {
        return trManager.run {
            when (it.matchRepository.getMatchStatusFromUser(userId)?.state) {
                MatchState.SETUP -> return@run failure(MatchCreationError.AlreadyInQueue(userId))
                MatchState.ONGOING -> return@run failure(MatchCreationError.UserAlreadyPlaying(userId))
                else -> Unit
            }

            if (isPrivate && (variant == null || size == null))
                return@run failure(MatchCreationError.InvalidPrivateMatch(size, variant))

            // generate a variant and a board
            val newVariant = if (variant != null) Variant.fromString(variant) else Variant.getRandom()
            val newBoard = newVariant.createBoard(size)

            // if it's a public match then find one with same preferences
            if (!isPrivate) {
                val match = it.matchRepository.getPublicMatchByPreferences(newBoard.size, newVariant.name)
                if (match != null) {
                    // match found, adding user to match
                    return@run success(
                        MatchCreationOutputModel(
                            it.matchRepository.updateMatch(
                                match.id,
                                whiteId = userId,
                                state = MatchState.ONGOING.name
                            ),
                            MatchState.ONGOING
                        )
                    )
                }
            }

            // create a new match(with board associated)
            return@run success(
                MatchCreationOutputModel(
                    it.matchRepository.createMatch(
                        userId,
                        isPrivate,
                        newVariant.name,
                        newBoard.size,
                        newBoard::class.java.simpleName
                    ),
                    MatchState.SETUP
                )
            )
        }
    }

    fun joinPrivateMatch(id: String, userId: Int): Either<MatchError, MatchCreationOutputModel> {
        return trManager.run {
            val currentMatchStatus = it.matchRepository.getMatchStatusFromUser(userId)

            when (currentMatchStatus?.state) {
                MatchState.SETUP -> {
                    if (currentMatchStatus.id == id) return@run success(
                        MatchCreationOutputModel(currentMatchStatus.id, MatchState.SETUP)
                    )
                    return@run failure(MatchCreationError.AlreadyInQueue(userId))
                }

                MatchState.ONGOING, MatchState.FINISHED ->
                    return@run success(
                        MatchCreationOutputModel(currentMatchStatus.id, MatchState.ONGOING)
                    )

                null -> Unit // user is not in a match
            }

            // if it's not in a match, then join the private match
            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            when (match.state) {
                MatchState.SETUP -> {
                    if (!match.isPrivate) return@run failure(MatchJoiningError.MatchIsNotPrivate(id))
                    return@run success(
                        MatchCreationOutputModel(
                            it.matchRepository.updateMatch(
                                id,
                                whiteId = userId,
                                state = MatchState.ONGOING.toString()
                            ),
                            MatchState.ONGOING
                        )
                    )
                }

                MatchState.ONGOING, MatchState.FINISHED -> return@run failure(MatchJoiningError.AlreadyStarted(id))
            }
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

    fun getMatchesFromUser(idUser: Int, skip: Int, limit: Int): MatchesFromUserOutputModel {
        return trManager.run { transaction ->
            val matchesCollection = transaction.matchRepository.getMatchesFromUser(idUser, skip, limit)
            MatchesFromUserOutputModel(
                matches = matchesCollection.results.map { it.toOutputModel() },
                total = matchesCollection.total,
            )
        }
    }

    fun play(userId: Int, id: String, dot: Dot): Either<MatchFetchingError, PlayOutputModel> {
        return trManager.run {
            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!isUserInMatch(userId, match))
                return@run failure(MatchFetchingError.UserNotInMatch(userId, match.id))

            val player = match.getPlayer(userId)
            val newBoard = match.play(dot, player).board

            val isMatchOver = newBoard is BoardWinner || newBoard is BoardDraw
            val newState = if (isMatchOver) MatchState.FINISHED else MatchState.ONGOING
            if (isMatchOver) it.matchRepository.updateMatch(id, state = newState.name)

            it.boardRepository.updateBoard(
                id,
                newBoard::class.java.simpleName,
                newBoard.stones.serialize(),
                newBoard.turn.symbol
            )
            success(PlayOutputModel(Stone(player, dot), newState.name))
        }
    }

    fun deleteSetupMatch(userId: Int) {
        return trManager.run {
            it.matchRepository.deleteMatch(userId)
        }
    }

    private fun isUserInMatch(idUser: Int, match: Match) =
        match.blackId == idUser || match.whiteId == idUser
}
