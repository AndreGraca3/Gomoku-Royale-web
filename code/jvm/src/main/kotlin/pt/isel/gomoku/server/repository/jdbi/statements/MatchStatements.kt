package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (isPrivate, variant, black_id, white_id)
        VALUES (:isPrivate, :variant, :black_id, :white_id)
    """

    const val CREATE_BOARD = """
        INSERT INTO board (match_id, type, size, stones, turn)
        VALUES (:match_id, :type, :size, :stones, :turn)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, isPrivate, variant, black_id, white_id, state, type, size, stones, turn
        FROM match join board on match.id = board.match_id
        where id = :id
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