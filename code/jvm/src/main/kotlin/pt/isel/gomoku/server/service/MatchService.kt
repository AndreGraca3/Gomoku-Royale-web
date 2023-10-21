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
            if (it.matchRepository.isUserInMatch(userId))
                return@run failure(MatchCreationError.AlreadyInMatch(userId))

            // there is a user with the same preferences as this user
            val match = it.matchRepository.getMatchByPreferences(isPrivate, size, variant)
            if (match != null)
                return@run success(
                    MatchCreationOutput(
                        it.matchRepository.addToMatch(
                            match.id,
                            userId
                        )
                    )
                )

            // waiting for other player
            val newVariant = if (variant != null) Variant.from(variant) else Variant.getRandom()

            val newBoard = newVariant.createBoard(size)

            val idMatch = it.matchRepository.createMatch(
                isPrivate,
                newVariant.name,
                userId
            )

            it.boardRepository.createBoard(idMatch, newBoard.size, newVariant.name)

            return@run success(
                MatchCreationOutput(
                    idMatch
                )
            )
        }
    }

    fun joinPrivateMatch(id: String, userId: Int): Either<MatchError, MatchCreationOutput> {
        return trManager.run {
            TODO("Not yet implemented")
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