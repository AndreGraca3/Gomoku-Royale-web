package pt.isel.gomoku.server.repository.interfaces

interface BoardRepository {
    fun createBoard(matchId: String, size: Int, type: String)

    fun updateBoard(matchId: String, stones: String, turn: Char)
}