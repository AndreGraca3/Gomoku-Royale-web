package pt.isel.gomoku.server.repository.jdbi.statements

object QueueStatements {
    const val CREATE_QUEUE =
        "insert into queue (player_id, isPrivate, size, variant) " +
                "VALUES (:player_id, :isPrivate, :size, :variant)"

    const val GET_QUEUE_BY_PREFERENCES =
        "select * from queue where isPrivate = :isPrivate and size = :size and variant = :variant"

    const val REMOVE_FROM_QUEUE =
        "delete from queue where match_id = :match_id"
}