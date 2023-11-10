package pt.isel.gomoku.server.repository.jdbi.statements

object StatsStatements {

    const val GET_TOP_RANKS = """
        SELECT id, u.name as userName, r.name as rankName, r.icon_url as rankImage
        FROM "user" u
        INNER JOIN rank r on u.rank = r.name
        ORDER BY u.mmr DESC
        LIMIT :limit
    """

    const val GET_WINS_BY_USER = """
        SELECT
            count(*) as total_matches,
            sum(case when bw.turn = 'B' and m.black_id = :userId then 1 else 0 end) wins_as_black,
            sum(case when bw.turn = 'W' and m.white_id = :userId then 1 else 0 end) as wins_as_white,
            sum(case when bw.type = 'BoardDraw' then 1 else 0 end) as draws
        FROM match m JOIN board bw on m.id = bw.match_id
    """

    const val GET_MATCHES_PLAYED_BY_USER = """
        SELECT
            count(*) as total_matches,
            sum(case when black_id = :userId then 1 else 0 end) as matches_as_black,
            sum(case when white_id = :userId then 1 else 0 end) as matches_as_white
        FROM match
        WHERE black_id = :userId or white_id = :userId;
    """
}