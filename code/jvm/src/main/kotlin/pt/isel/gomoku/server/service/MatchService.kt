package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.game.Match
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
    ): Either<MatchCreationError, MatchCreationOutput> {
        return trManager.run {
            // if user is already in queue, return error
            if (it.lobbyRepository.getLobbiesByUser(userId).isNotEmpty())
                return@run failure(MatchCreationError.AlreadyInQueue(userId))

            // if private match, size and variant must be specified
            if (isPrivate && (variant == null || size == null))
                return@run failure(MatchCreationError.InvalidPrivateMatch(size, variant))

            // check if variant and size are valid
            val newVariant = if (variant != null) Variant.from(variant) else Variant.getRandom()
            val newBoard = newVariant.createBoard(size)

            // if it's private or no suitable public match is found, create lobby
            val lobby = (if (isPrivate) null else it.lobbyRepository.getPublicLobbyByPreferences(size, variant))
                ?: return@run success(
                    MatchCreationOutput(
                        it.lobbyRepository.createLobby(
                            userId,
                            isPrivate,
                            size,
                            variant
                        ),
                        false
                    )
                )

            // create public match and remove lobby
            val matchId = it.matchRepository.createMatch(
                lobby.id, false, newVariant.name, newBoard.serialize(), lobby.playerId, userId
            )
            it.lobbyRepository.removeLobby(lobby.id)
            success(MatchCreationOutput(matchId, true))
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
                lobby.id, true, newVariant.name, newBoard.serialize(), lobby.playerId, userId
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