package pt.isel.gomoku.server.repository.jdbi.statements

object UserStatements {
    const val CREATE_USER =
        "insert into \"user\" (name, email, password, avatar) values (:name, :email, :password, :avatar)"

    const val GET_USER_BY_ID =
        "select id, name, email, avatar, role from \"user\" where id = :id"

    const val GET_USER_BY_NAME =
        "select id, name, email, avatar, role from \"user\" where name = :name"

    const val GET_USER_BY_EMAIL =
        "select id, name, email, avatar, role from \"user\" where email = :email"

    const val GET_USERS =
        "select id, name from \"user\" where role = :role OR :role IS NULL"
}