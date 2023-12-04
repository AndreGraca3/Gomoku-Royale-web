package pt.isel.gomoku.server.repository.jdbi.statements

object UserStatements {

    const val GET_USER_BASE = """
        select id, u.name, email, password, role, avatar_url, created_at, rank, icon_url
        from "user" u
        join stats s on u.id = s.user_id
        join rank r on s.rank = r.name
    """

    const val GET_USER_INFO_BASE = """
        select id, u.name, email, avatar_url, role, created_at, rank, icon_url
        from "user" u
        join stats s on u.id = s.user_id
        join rank r on s.rank = r.name
    """

    const val GET_USER_DETAILS_BASE = """
        select id, name, email, avatar_url, role, created_at
        from "user" u
    """

    const val GET_USER_ITEM_BASE = """
        select id, name, role, ${PaginationStatements.PAGINATION_PREFIX}
        from "user" u
    """

    const val CREATE_USER =
        "insert into \"user\" (name, email, password, avatar_url) values (:name, :email, :password, :avatar_url)"

    const val GET_USER_BY_ID =
        """
            $GET_USER_INFO_BASE
            where id = :id
        """

    const val GET_USER_BY_NAME =
        "select id, name, email, avatar_url, role from \"user\" where name = :name"

    const val GET_USER_BY_EMAIL =
        "$GET_USER_BASE where email = :email"

    const val GET_USERS =
        """
        $GET_USER_ITEM_BASE
        where role = coalesce(:role, role)
        ${PaginationStatements.PAGINATION_SUFFIX}
        """

    const val UPDATE_USER =
        """
        update "user"
        set name = coalesce(:name, name), avatar_url = coalesce(:avatar_url, avatar_url)
        where id = :id
        """

    const val DELETE_USER =
        "delete from \"user\" where id = :id"

    const val CREATE_TOKEN =
        "insert into token (token_value, user_id) values (:token_value, :user_id)"

    const val GET_USER_AND_TOKEN_BY_TOKEN_VALUE =
        """
                select id, u.name, email, password, role, avatar_url, rank, icon_url, u.created_at as user_created_at,
                t.token_value, t.created_at as token_created_at, t.last_used
                from "user" u
                join stats as s on u.id = s.user_id
                join rank as r on s.rank = r.name
                join token as t
                on u.id = t.user_id
                where token_value = :token_value
        """

    const val UPDATE_TOKEN_LAST_USED =
        "update token set last_used = :last_used where token_value = :token_value"

    const val DELETE_TOKEN =
        "delete from token where token_value = :token_value"

    const val GET_TOKEN_BY_USER_ID =
        "select token_value, user_id, created_at, last_used from token where user_id = :user_id"
}
