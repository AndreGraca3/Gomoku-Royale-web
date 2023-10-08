package pt.isel.gomoku.server.repository.jdbi.statements

object UserStatements {
    const val CREATE_USER =
        "insert into \"user\" (name, email, password, avatar_url) values (:name, :email, :password, :avatar_url)"

    const val GET_USER_BY_ID =
        "select id, name, email, avatar_url, role from \"user\" where id = :id"

    const val GET_USER_BY_NAME =
        "select id, name, email, avatar_url, role from \"user\" where name = :name"

    const val GET_USER_BY_EMAIL =
        "select id, name, email, password, avatar_url, role from \"user\" where email = :email"

    const val GET_USERS =
        "select id, name from \"user\" where role = :role OR :role IS NULL"

    const val UPDATE_USER =
        """
                update "user"
                set name = coalesce(:name, name), avatar_url = coalesce(:avatar_url, avatar_url), role = coalesce(:role, role)
                where id = :id
        """

    const val DELETE_USER =
        "delete from \"user\" where id = :id"

    const val CREATE_TOKEN =
        "insert into token (token_value, user_id, created_at, last_used) values (:token_value, :user_id, :created_at, :last_used)"

    const val GET_USER_AND_TOKEN_BY_TOKEN_VALUE =
        """
                select id, name, email, password, role, avatar_url, t.token_value, t.created_at, t.last_used
                from "user" u
                inner join token as t
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