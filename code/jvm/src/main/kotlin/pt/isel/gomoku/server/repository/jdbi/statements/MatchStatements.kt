package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (isPrivate, variant, black_id, state)
        VALUES (:isPrivate, :variant, :black_id, 'WAITING_FOR_PLAYERS')
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, isPrivate, variant, board, black_id, white_id, winner_id
        FROM match
        WHERE id = :id
    """

    const val GET_MATCH_BY_PREFERENCES = """
        SELECT id, isPrivate, variant, black_id, white_id, turn, size, type, stones
        FROM match m
        INNER JOIN board b ON b.match_id = m.id
        WHERE isPrivate = :isPrivate and size = :size and variant = :variant and state = 'WAITING_FOR_PLAYERS';
    """

    const val ADD_USER_TO_MATCH = """
        UPDATE match
        SET player_white = :player_white, 
        state = 'IN_PROGRESS'
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

    const val IS_USER_IN_MATCH = """
        SELECT * 
        FROM match
        WHERE (black_id = :userId or white_id = :userId) and (state = 'IN_PROGRESS' or state = 'WAITING_FOR_PLAYERS')
    """
}