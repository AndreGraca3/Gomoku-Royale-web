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
        SELECT wins_as_black, wins_as_white, draws
        FROM stats
        WHERE user_id = :userId;
    """

    const val GET_MATCHES_PLAYED_BY_USER = """
        SELECT (matches_as_black + matches_as_white) as total_matches, matches_as_black, matches_as_white
        FROM stats 
        WHERE user_id = :userId;
    """

    const val GET_RANK = """
        SELECT r.name, r.icon_url as iconUrl
        FROM "user" u
        INNER JOIN stats s on u.id = s.user_id
        INNER JOIN rank r on s.rank = r.name
        where user_id = :userId;
    """

    const val UPDATE_WIN_STATS = """
        UPDATE stats
        SET wins_as_black = CASE WHEN user_id = :user_id AND :player = 'B' THEN wins_as_black + 1 ELSE wins_as_black END,
            wins_as_white = CASE WHEN user_id = :user_id AND :player = 'W' THEN wins_as_white + 1 ELSE wins_as_white END;
    """

    const val UPDATE_MMR = """
        UPDATE "user"
        SET mmr = CASE WHEN (:mmrChange < 0 AND mmr < ABS(:mmrChange)) THEN 0 ELSE mmr + :mmrChange END
        WHERE id = :userId
        RETURNING mmr;
    """
}