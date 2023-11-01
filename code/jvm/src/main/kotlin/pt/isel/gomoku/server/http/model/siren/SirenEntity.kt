package pt.isel.gomoku.server.http.model.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class SirenEntity<T> (
    @JsonProperty("class") val clazz: List<String>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val links: List<SirenLink>? = null,
    val actions: List<SirenAction>? = null,
    val title: String? = null
)