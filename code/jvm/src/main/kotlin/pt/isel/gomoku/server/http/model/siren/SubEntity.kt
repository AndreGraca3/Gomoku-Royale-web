package pt.isel.gomoku.server.http.model.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

sealed class SubEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
class EmbeddedLink(
    @JsonProperty("class") val clazz: String? = null,
    val rel: List<String>,
    val href: String,
    val type: String? = null,
    val title: String? = null
) : SubEntity() {
    init {
        require(rel.isNotEmpty()) { "rel field MUST be a non-empty array of strings!" }
    }
}

class EmbeddedRepresentation<T>(
    @JsonProperty(value = "class") val clazz: List<String>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val links: List<SirenLink>? = null,
    val actions: List<SirenAction>? = null,
    val title: String? = null,
    val rel: List<String>
) : SubEntity()