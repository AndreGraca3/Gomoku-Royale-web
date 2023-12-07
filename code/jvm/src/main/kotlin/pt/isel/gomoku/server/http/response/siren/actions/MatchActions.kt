package pt.isel.gomoku.server.http.response.siren.actions

import org.springframework.http.HttpMethod
import pt.isel.gomoku.server.http.INPUT_CONTENT_TYPE
import pt.isel.gomoku.server.http.Uris
import pt.isel.gomoku.server.http.response.siren.SirenAction
import pt.isel.gomoku.server.http.response.siren.SirenActionField
import pt.isel.gomoku.server.http.response.siren.SirenActionFieldType
import java.net.URI

object MatchActions {

    fun getCreatePublicMatchAction() = SirenAction(
        name = "create-public-match",
        href = URI(Uris.Matches.BASE),
        method = HttpMethod.POST,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "isPrivate",
                type = SirenActionFieldType.text
            ),
            SirenActionField(
                name = "size",
                type = SirenActionFieldType.number
            ),
            SirenActionField(
                name = "variant",
                type = SirenActionFieldType.text
            )
        )
    )

    fun getJoinPrivateMatchAction(id: String) = SirenAction(
        name = "join-private-match",
        href = Uris.Matches.buildMatchByIdUri(id),
        method = HttpMethod.PUT,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "id",
                type = SirenActionFieldType.number
            )
        )
    )

    fun getPlayMatchAction(id: String) = SirenAction(
        name = "play-match",
        href = Uris.Matches.buildMatchByIdUri(id),
        method = HttpMethod.POST,
        type = INPUT_CONTENT_TYPE,
        fields = listOf(
            SirenActionField(
                name = "id",
                type = SirenActionFieldType.text,
            ),
            SirenActionField(
                name = "row",
                type = SirenActionFieldType.number
            ),
            SirenActionField(
                name = "column",
                type = SirenActionFieldType.text
            )
        )
    )

    fun getDeleteMatchAction() = SirenAction(
        name = "delete-match",
        href = URI(Uris.Matches.BASE),
        method = HttpMethod.DELETE,
        type = INPUT_CONTENT_TYPE,
    )
}