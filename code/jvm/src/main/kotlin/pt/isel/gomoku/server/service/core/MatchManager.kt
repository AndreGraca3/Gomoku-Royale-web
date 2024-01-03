package pt.isel.gomoku.server.service.core

import pt.isel.gomoku.domain.Match

class MatchManager(val mmrPerWin: Int, val mmrPerLoss: Int) {
    fun canForfeit(match: Match, userId: Int): Boolean {
        // apply business rules
        return true
    }
}