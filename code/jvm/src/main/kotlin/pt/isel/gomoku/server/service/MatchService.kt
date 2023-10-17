package pt.isel.gomoku.server.service

import org.springframework.stereotype.Component
import pt.isel.gomoku.domain.game.Match
import pt.isel.gomoku.domain.game.Variant
import pt.isel.gomoku.domain.game.board.BoardWinner
import pt.isel.gomoku.domain.game.cell.Dot
import pt.isel.gomoku.server.http.model.match.MatchCreationOutput
import pt.isel.gomoku.server.repository.transaction.managers.TransactionManager
import pt.isel.gomoku.server.service.error.match.MatchCreationError
import pt.isel.gomoku.server.service.error.match.MatchFetchingError
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
            // Lobby logic could be extracted to a LobbyDomainService
            if (it.lobbyRepository.getLobbiesByUser(userId).isNotEmpty())
                return@run failure(MatchCreationError.AlreadyInQueue(userId))

            val lobby = it.lobbyRepository.getLobbyByPreferences(isPrivate, size, variant)
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

            // Lobby with same preferences found, create Match...
            val newVariant = if (variant != null) Variant.from(variant) else Variant.getRandom()
            val newBoard = newVariant.createBoard(lobby.size)

            val matchId = it.matchRepository.createMatch(
                lobby.id, isPrivate, newVariant.name, newBoard.serialize(), lobby.playerId, userId
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