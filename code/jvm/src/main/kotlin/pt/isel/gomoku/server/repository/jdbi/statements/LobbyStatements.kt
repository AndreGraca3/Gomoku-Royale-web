package pt.isel.gomoku.server.repository.jdbi.statements

object LobbyStatements {
    const val CREATE_LOBBY =
        "insert into lobby (player_id, isPrivate, size, variant) " +
                "VALUES (:player_id, :isPrivate, :size, :variant)"

    const val GET_LOBBY_BY_ID =
        "select * from lobby where id = :id"

    const val GET_LOBBY_BY_PREFERENCES =
        "select * from lobby where isPrivate = false and size = :size or size is null and variant = :variant or variant is null;"

    const val GET_LOBBIES_BY_USER =
        "select * from lobby where player_id = :player_id"

    const val REMOVE_FROM_LOBBY =
        "delete from lobby where id = :id"
}