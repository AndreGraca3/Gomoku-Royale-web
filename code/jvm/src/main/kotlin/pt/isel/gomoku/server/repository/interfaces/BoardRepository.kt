package pt.isel.gomoku.server.repository.interfaces

interface BoardRepository {
    fun createBoard(match_id: String, size: Int, type: String)
}