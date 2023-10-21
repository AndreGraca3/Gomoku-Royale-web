package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.MatchState
import pt.isel.gomoku.domain.game.Variant
import pt.isel.gomoku.domain.game.board.BoardWinner
import pt.isel.gomoku.domain.game.cell.Dot
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
    ): Either<MatchCreationError, Match> {
        return trManager.run {

            val newVariant = if (variant != null) Variant.from(variant) else Variant.getRandom()
            val newBoard = newVariant.createBoard(size)

            val matchId = it.matchRepository.createMatch(
                isPrivate = isPrivate,
                serializedVariant = variant ?: Variant.getRandom().name,
                board = newBoard,
                blackId = userId,
                whiteId = null
            )

            return@run success(
                Match(
                    id = matchId,
                    isPrivate = isPrivate,
                    variant = newVariant,
                    board = newBoard,
                    blackId = userId,
                    whiteId = null,
                    state = MatchState.SETUP
                )
            )
        }
    }

    fun joinPrivateMatch(id: String, userId: Int): Either<MatchError, MatchCreationOutput> {
        return trManager.run {
            val lobby = it.lobbyRepository.getLobbyById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!lobby.isPrivate) return@run failure(MatchJoiningError.MatchIsNotPrivate(id))

            if (lobby.playerId == userId) return@run failure(MatchCreationError.AlreadyInQueue(userId))

            val newVariant = Variant.from(lobby.variant!!)
            val newBoard = newVariant.createBoard(lobby.size)

            val matchId = it.matchRepository.createMatch(
                true, newVariant.name, newBoard, lobby.playerId, userId
            )
            it.lobbyRepository.removeLobby(lobby.id)
            success(MatchCreationOutput(matchId, true))
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

    fun play(userId: Int, id: String, dot: Dot): Either<MatchFetchingError, Unit> {
        return trManager.run {
            val match = it.matchRepository.getMatchById(id)
                ?: return@run failure(MatchFetchingError.MatchByIdNotFound(id))

            if (!isUserInMatch(userId, match))
                return@run failure(MatchFetchingError.UserNotInMatch(userId, match.id))

            val player = match.getPlayer(userId)
            val newMatch = match.play(dot, player)
            success(
                it.matchRepository.updateMatch(
                    id,
                    serializedBoard = newMatch.board.serialize(),
                    winnerId = if (newMatch.board is BoardWinner) userId else null
                )
            )
        }
    }

    private fun isUserInMatch(idUser: Int, match: Match) =
        match.blackId == idUser || match.whiteId == idUser
}