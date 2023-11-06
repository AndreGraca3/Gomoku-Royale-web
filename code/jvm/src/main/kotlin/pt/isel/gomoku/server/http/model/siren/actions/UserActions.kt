package pt.isel.gomoku.server.http.model.siren.actions

import pt.isel.gomoku.server.http.model.siren.SirenAction

object UserActions {
    val CREATE_USER_ACTION : SirenAction = SirenAction(
        name = "create-user",
        href = "/users",
        method = "POST",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "name",
                type = "text"
            ),
            SirenAction.SirenField(
                name = "email",
                type = "email"
            ),
            SirenAction.SirenField(
                name = "password",
                type = "password"
            ),
            SirenAction.SirenField(
                name = "avatarUrl",
                type = "url"
            )
        )
    )
    val GET_USER_ACTION : SirenAction = SirenAction(
        name = "get-user",
        href = "/users/{id}",
        method = "GET",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "id",
                type = "number"
            )
        )
    )

    val UPDATE_USER_ACTION : SirenAction = SirenAction(
        name = "update-user",
        href = "/users/{id}",
        method = "PATCH",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "id",
                type = "number"
            ),
            SirenAction.SirenField(
                name = "name",
                type = "text"
            ),
            SirenAction.SirenField(
                name = "avatarUrl",
                type = "url"
            )
        )
    )

    val DELETE_USER_ACTION : SirenAction = SirenAction(
        name = "delete-user",
        href = "/users",
        method = "DELETE",
        type = "application/json"
    )

    val CREATE_TOKEN_ACTION : SirenAction = SirenAction(
        name = "create-token",
        href = "/users/token",
        method = "POST",
        type = "application/json",
        fields = listOf(
            SirenAction.SirenField(
                name = "email",
                type = "email"
            ),
            SirenAction.SirenField(
                name = "password",
                type = "password"
            )
        )
    )

}