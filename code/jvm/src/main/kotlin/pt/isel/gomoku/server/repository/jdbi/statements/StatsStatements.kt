package pt.isel.gomoku.server.repository.jdbi.statements

object StatsStatements {
    const val CREATE_ENTRY = """
        INSERT INTO stats (user_id) values (:userId)
    """

    const val GET_TOP_RANKS = """
        SELECT id, u.name as userName, r.name as rankName, r.icon_url as iconUrl
        FROM "user" u
        INNER JOIN stats s on u.id = s.user_id
        INNER JOIN rank r on s.rank = r.name
        ORDER BY u.mmr DESC
        LIMIT :limit
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