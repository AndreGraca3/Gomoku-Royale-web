package pt.isel.gomoku.server.repository.jdbi.statements

object MatchStatements {

    const val CREATE_MATCH = """
        INSERT INTO match (id, isPrivate, board, player_black, player_white)
        VALUES (:id, :isPrivate, :board, :player_black, :player_white)
    """

    const val GET_MATCH_BY_ID = """
        SELECT id, isPrivate, board, player_black, player_white
        FROM match
        WHERE id = :id
    """

    const val GET_MATCHES_BY_USER_ID = """
        SELECT id, isPrivate, board, player_black, player_white 
        FROM match
        where player_black = :idUser or player_white = :idUser
    """

    const val UPDATE_MATCH = """
        UPDATE match
        SET board = coalesce(:board, board),
        player_black = coalesce(:player_black, player_black),
        player_white = coalesce(:player_white, player_white),
        winner_id = coalesce(:winner_id, winner_id)
        WHERE id = :id
    """
}