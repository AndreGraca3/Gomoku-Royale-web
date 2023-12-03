package pt.isel.gomoku.server.repository.jdbi.statements

object StatsStatements {
    const val CREATE_ENTRY = """
        INSERT INTO stats (user_id) values (:userId)
    """

    const val GET_TOP_RANKS = """
        ${UserStatements.GET_USER_ITEM_BASE}
        ORDER BY u.mmr DESC
        ${PaginationStatements.PAGINATION_SUFFIX}
    """

    const val GET_WINS_BY_USER = """
        SELECT (matches_as_black + matches_as_white) as total_matches, wins_as_black, wins_as_white, draws
        FROM stats
        WHERE user_id = :userId;
    """

    const val GET_MATCHES_PLAYED_BY_USER = """
        SELECT (matches_as_black + matches_as_white) as total_matches, matches_as_black, matches_as_white
        FROM stats 
        WHERE user_id = :userId;
    """
}