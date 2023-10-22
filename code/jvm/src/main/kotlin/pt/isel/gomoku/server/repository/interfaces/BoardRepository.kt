package pt.isel.gomoku.server.repository.interfaces

interface BoardRepository {
    fun updateBoard(matchId: String,type: String, stones: String, turn: Char)
}