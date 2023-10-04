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
        "update \"user\" set avatar_url = :avatar, role = :role where name = :name"

    const val DELETE_USER =
        "delete from \"user\" where id = :id"

    const val CREATE_TOKEN =
        "insert into token (token_value, user_id, created_at, last_used) values (:token_value, :user_id, :created_at, :last_used)"
}