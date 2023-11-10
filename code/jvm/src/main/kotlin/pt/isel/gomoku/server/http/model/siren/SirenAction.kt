package pt.isel.gomoku.server.http.model.siren

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.RequestMapping
import pt.isel.gomoku.server.http.model.user.UserIdOutput
import kotlin.coroutines.cancellation.CancellationException

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class SirenField(
        val name: String,
        @JsonProperty("class") val clazz: String? = null,
        val type: String? = null,
        val value: String? = null,
        val title: String? = null
    )
}