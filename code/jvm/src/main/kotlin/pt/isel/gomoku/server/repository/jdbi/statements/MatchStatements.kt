package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (id, isPrivate, variant, board, black_id, white_id)
        VALUES (:id, :isPrivate, :variant, :board, :black_id, :white_id)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, isPrivate, variant, board, black_id, white_id, winner_id
        FROM match
        WHERE id = :id
    """

    const val GET_MATCHES_BY_USER_ID = """
        SELECT id, isPrivate, board, black_id, white_id, winner_id 
        FROM match
        where black_id = :userId or white_id = :userId
    """

    const val UPDATE_MATCH = """
        UPDATE match
        SET board = coalesce(:board, board),
        black_id = coalesce(:black_id, black_id),
        white_id = coalesce(:white_id, white_id),
        winner_id = coalesce(:winner_id, winner_id)
        WHERE id = :id
    """
}