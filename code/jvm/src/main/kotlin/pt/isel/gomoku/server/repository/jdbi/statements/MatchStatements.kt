package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (id, isPrivate, board, player_black, player_white)
        VALUES (:id, :isPrivate, :board, :player_black, :player_white)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, isPrivate, board, variant, created_at, player1_id, player2_id, winner_id
        FROM match
        WHERE id = :id
    """

    const val UPDATE_MATCH = """
        UPDATE match
        SET visibility = coalesce(:isPrivate, isPrivate), winner_id = coalesce(:winner_id, winner_id)
        WHERE id = :id
    """
}