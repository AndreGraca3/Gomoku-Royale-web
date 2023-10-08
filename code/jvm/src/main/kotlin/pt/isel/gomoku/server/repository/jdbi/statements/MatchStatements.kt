package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (visibility, board, variant, player1_id)
        VALUES (:visibility, :board, :variant, :player1_id)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, visibility, board, variant, created_at, player1_id, player2_id, winner_id
        FROM match
        WHERE id = :id
    """

    const val UPDATE_MATCH = """
        UPDATE match
        SET visibility = coalesce(:visibility, visibility), winner_id = coalesce(:winner_id, winner_id)
        WHERE id = :id
    """
}