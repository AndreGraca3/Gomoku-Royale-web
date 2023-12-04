package pt.isel.gomoku.server.http

import org.springframework.http.MediaType
import org.springframework.web.util.UriTemplate
import pt.isel.gomoku.server.http.response.siren.SirenLink
import java.net.URI

val INPUT_CONTENT_TYPE = MediaType.APPLICATION_JSON

object Uris {
    const val API_BASE = "/api"

    object Users {
        const val BASE = "$API_BASE/users"
        const val TOKEN = "$BASE/token"
        const val AUTHENTICATED_USER = "$BASE/me"
        const val USER_BY_ID = "$BASE/{id}"

        fun buildUserByIdUri(id: Int) = UriTemplate(USER_BY_ID).expand(id)
    }

    object Matches {
        const val BASE = "$API_BASE/matches"
        const val MATCH_BY_ID = "$BASE/{id}"

        fun buildMatchByIdUri(id: String) = UriTemplate(MATCH_BY_ID).expand(id)
    }

    object Stats {
        const val BASE = "$API_BASE/stats"
        const val STATS_BY_USER_ID = "$BASE/users/{id}"
        const val TOP = "$BASE/users/top"

        fun buildStatsByUserIdUri(id: Int) = UriTemplate(STATS_BY_USER_ID).expand(id)
    }

    object Pagination {
        private const val PAGINATION_QUERY = "?skip={skip}&limit={limit}"
        const val MAX_LIMIT = 50

        fun getPaginationSirenLinks(
            uri: URI,
            skip: Int,
            limit: Int,
            total: Int,
        ): List<SirenLink> {
            if (skip !in 0 until total) return listOf(
                SirenLink(
                    listOf("self"),
                    UriTemplate("${uri}$PAGINATION_QUERY").expand(0, MAX_LIMIT)
                ),
            )
            val links = mutableListOf(
                SirenLink(listOf("self"), UriTemplate("${uri}$PAGINATION_QUERY").expand(skip, limit)),
            )

            if (skip > 0)
                links.add(
                    SirenLink(
                        listOf("prev"),
                        UriTemplate("${uri}$PAGINATION_QUERY")
                            .expand(skip - 1, limit)
                    )
                )

            if (total - 1 > skip)
                links.add(
                    SirenLink(
                        listOf("next"),
                        UriTemplate("${uri}$PAGINATION_QUERY")
                            .expand(skip + 1, limit)
                    )
                )

            return links
        }
    }
}