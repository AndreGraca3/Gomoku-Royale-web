package pt.isel.gomoku.server.http.response.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.gomoku.server.http.response.Response
import java.net.URI

@JsonInclude(JsonInclude.Include.NON_NULL)
class Siren<T>(
    @JsonProperty("class") val clazz: List<SirenClass>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val links: List<SirenLink>? = null,
    val actions: List<SirenAction>? = null,
    val title: String? = null,
) : Response() {
    override fun getContentType() = "application/vnd.siren+json"
}

sealed class SubEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
class EmbeddedLink(
    @JsonProperty("class") val clazz: List<SirenClass>? = null,
    val rel: List<String>,
    val href: URI,
    val type: String? = null,
    val title: String? = null
) : SubEntity() {
    init {
        require(rel.isNotEmpty()) { "rel field MUST be a non-empty array of strings!" }
    }
}

class EmbeddedRepresentation<T>(
    @JsonProperty(value = "class") val clazz: List<SirenClass>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val links: List<SirenLink>? = null,
    val actions: List<SirenAction>? = null,
    val title: String? = null,
    val rel: List<String>
) : SubEntity()

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenLink(
    val rel: List<String>,
    val href: URI,
) {
    companion object {
        fun previous(href: URI) = SirenLink(rel = listOf("previous"), href = href)

        fun self(href: URI) = SirenLink(rel = listOf("self"), href = href)

        fun next(href: URI) = SirenLink(rel = listOf("next"), href = href)
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class SirenAction(
    val name: String,
    val title: String? = null,
    @JsonSerialize(using = ToStringSerializer::class) val method: HttpMethod? = null,
    val href: URI,
    @JsonSerialize(using = ToStringSerializer::class) val type: MediaType? = null,
    val fields: List<SirenActionField>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SirenActionField(
    val name: String,
    val title: String? = null,
    val type: SirenActionFieldType? = null,
    val value: String? = null,
)