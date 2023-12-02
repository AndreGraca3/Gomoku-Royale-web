package pt.isel.gomoku.server.http.response.siren.actions

import org.springframework.http.HttpMethod
import pt.isel.gomoku.server.http.INPUT_CONTENT_TYPE
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.response.siren.SirenAction
import pt.isel.gomoku.server.http.response.siren.SirenActionField
import pt.isel.gomoku.server.http.response.siren.SirenActionFieldType
import java.net.URI

object UserActions {
    fun getCreateUserAction() = SirenAction(
        name = "create-user",
        href = URI(Uris.Users.BASE),
        method = HttpMethod.POST,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "name",
                type = SirenActionFieldType.text
            ),
            SirenActionField(
                name = "email",
                type = SirenActionFieldType.email
            ),
            SirenActionField(
                name = "password",
                type = SirenActionFieldType.password
            ),
            SirenActionField(
                name = "avatarUrl",
                type = SirenActionFieldType.url
            )
        )
    )

    fun getUpdateUserAction(id: Int) = SirenAction(
        name = "update-user",
        href = Uris.Users.buildUuserByIdUri(id),
        method = HttpMethod.PATCH,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "id",
                type = SirenActionFieldType.number
            ),
            SirenActionField(
                name = "name",
                type = SirenActionFieldType.text
            ),
            SirenActionField(
                name = "avatarUrl",
                type = SirenActionFieldType.url
            )
        )
    )

    fun getDeleteUserAction() = SirenAction(
        name = "delete-user",
        href = URI(Uris.Users.BASE),
        method = HttpMethod.DELETE,
        type = INPUT_CONTENT_TYPE
    )

    fun getCreateTokenAction() : SirenAction = SirenAction(
        name = "create-token",
        href = URI(Uris.Users.TOKEN),
        method = HttpMethod.PUT,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "email",
                type = SirenActionFieldType.email
            ),
            SirenActionField(
                name = "password",
                type = SirenActionFieldType.password
            )
        )
    )
}