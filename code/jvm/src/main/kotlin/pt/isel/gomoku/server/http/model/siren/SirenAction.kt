package pt.isel.gomoku.server.http.model.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class SirenAction(
    val name: String,
    @JsonProperty("class") val clazz: String? = null,
    val method: String? = null,
    val href: String,
    val title: String? = null,
    val type: String? = null, // "application/x-www-form-urlencoded"
    val fields: List<SirenField>? = null
) {
    data class SirenField(
        val name: String,
        @JsonProperty("class") val clazz: String? = null,
        val type: String? = null,
        val value: String? = null,
        val title: String? = null
    )
}
