package pt.isel.gomoku.server.http.model.siren

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class SirenLink(
    val rel: List<String>,
    val clazz: List<String>? = null,
    val href: String,
    val title: String? = null,
    val type: String? = null,
) {
    companion object {
        fun self(href: String) = SirenLink(rel = listOf("self"), href = href)

        fun next(href: String) = SirenLink(rel = listOf("next"), href = href)

        fun previous(href: String) = SirenLink(rel = listOf("previous"), href = href)
    }
}

