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

        fun buildUuserByIdUri(id: Int) = UriTemplate(USER_BY_ID).expand(id)
    }

    object Matches {
        const val BASE = "$API_BASE/matches"
        const val MATCH_BY_ID = "/$BASE/{id}"

        fun buildMatchByIdUri(id: String) = UriTemplate(MATCH_BY_ID).expand(id)
    }

    object Stats {
        const val BASE = "$API_BASE/stats"
        const val STATS_BY_USER_ID = "$BASE/users/{id}"
        const val TOP = "$BASE/top"

        fun buildStatsByUserIdUri(id: Int) = UriTemplate(STATS_BY_USER_ID).expand(id)
    }

    object Pagination {
        private const val PAGINATION_QUERY = "?page={page}&limit={limit}"

        fun getPaginationSirenLinks(
            uri: URI,
            page: Int,
            limit: Int,
            pageSize: Int,
            collectionSize: Int,
        ): List<SirenLink> {
            val toReturn = mutableListOf(
                SirenLink(listOf("self"), UriTemplate("${uri}$PAGINATION_QUERY").expand(page, limit)),
            )

            if (page > 0 && collectionSize > 0)
                toReturn.add(
                    SirenLink(
                        listOf("prev"),
                        UriTemplate("${uri}$PAGINATION_QUERY")
                            .expand(page - 1, limit)
                    )
                )

            if (collectionSize > ((page + 1) * limit) && pageSize > 0)
                toReturn.add(
                    SirenLink(
                        listOf("next"),
                        UriTemplate("${uri}$PAGINATION_QUERY")
                            .expand(page + 1, limit)
                    )
                )

            return toReturn
        }
    }
}