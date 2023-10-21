package pt.isel.gomoku.server.repository.jdbi.statements

object BoardStatements {
    const val CREATE_BOARD = """
        INSERT INTO board (match_id, turn, size, stones, type)
        VALUES (:match_id, 'B', :size, '', :type)
    """
}