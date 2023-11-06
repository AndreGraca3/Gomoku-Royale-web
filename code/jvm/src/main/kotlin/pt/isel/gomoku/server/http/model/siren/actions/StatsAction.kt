package pt.isel.gomoku.server.http.model.siren.actions

import pt.isel.gomoku.server.http.model.siren.SirenAction

object StatsAction {

    val GET_TOP_STATS_ACTION = SirenAction(
        name = "get-top-stats",
        href = "/stats/top",
        method = "GET",
        type = "application/json",
        fields =
        listOf(
            SirenAction.SirenField(
                name = "limit",
                type = "number"
            )
        )
    )

    val GET_USER_STATS_ACTION = SirenAction(
        name = "get-user-stats",
        href = "/stats",
        method = "GET",
        type = "application/json",
        fields =
        listOf(
            SirenAction.SirenField(
                name = "id",
                type = "text"
            )
        )
    )
}