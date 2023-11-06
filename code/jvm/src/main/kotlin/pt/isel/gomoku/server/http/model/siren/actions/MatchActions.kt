package pt.isel.gomoku.server.http.model.siren.actions

import pt.isel.gomoku.server.http.model.siren.SirenAction

object MatchActions {

    val CREATE_MATCH_ACTION_PUBLIC: SirenAction = SirenAction(
        name = "create-match",
        href = "/matches",
        method = "POST",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "isPrivate",
                type = "text"
            ),
            SirenAction.SirenField(
                name = "size",
                type = "number"
            ),
            SirenAction.SirenField(
                name = "variant",
                type = "text"
            )
        )
    )

    val JOIN_PRIVATE_MATCH_ACTION: SirenAction = SirenAction(
        name = "create-match",
        href = "/matches/{id}/join",
        method = "POST",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "id",
                type = "text"
            )
        )
    )

    val GET_MATCH_ACTION: SirenAction = SirenAction(
        name = "get-match",
        href = "/matches/{id}",
        method = "GET",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "id",
                type = "text"
            )
        )
    )

    val GET_MATCHES_FROM_USER_ACTION: SirenAction = SirenAction(
        name = "get-matches-from-user",
        href = "/matches",
        method = "GET",
        type = "application/json",
        fields = null
    )

    val PLAY_MATCH_ACTION: SirenAction = SirenAction(
        name = "update-match",
        href = "/matches/{id}",
        method = "PATCH",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "id",
                type = "text"
            ),
            SirenAction.SirenField(
                name = "row",
                type = "number"
            ),
            SirenAction.SirenField(
                name = "column",
                type = "text"
            )
        )
    )

    val DELETE_MATCH_ACTION: SirenAction = SirenAction(
        name = "delete-match",
        href = "/matches",
        method = "DELETE",
        type = "application/json",
        fields = null
    )
}