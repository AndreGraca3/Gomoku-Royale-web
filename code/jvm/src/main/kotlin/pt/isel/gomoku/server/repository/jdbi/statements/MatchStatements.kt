package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (id, visibility, board, variant, created_at, player2_id, player2_id, winner_id)
        VALUES (:id, :visibility, :board, :variant, :created_at, :player1_id, :player2_id, :winner_id)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, visibility, board, variant, created_at, player1_id, player2_id, winner_id
        FROM match
        WHERE id = :id
    """
}