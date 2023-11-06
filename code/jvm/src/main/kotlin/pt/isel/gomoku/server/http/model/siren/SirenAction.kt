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
    data class SirenField(
        val name: String,
        @JsonProperty("class") val clazz: String? = null,
        val type: String? = null,
        val value: String? = null,
        val title: String? = null
    )
}

//fun getSirenActionListFrom(clazz: Class<*>, methodName: String, value: Any?): List<SirenAction> {
//    //obtain method -> clazz.getMethod(methodName)
//    val sirenList = mutableListOf<SirenAction>()
//    val methods = clazz.declaredMethods.filter { it.name != methodName }
//    for (method in methods) {
//        val href = method.declaredAnnotations.find { it.javaClass.isInstance(RequestMapping::class.java) } ?: throw CancellationException("andré é feio")
//
//        SirenAction(
//            name = method.name,
//            clazz = clazz.simpleName,
//            method = method.name,
//            href = href,
//            fields = listOf()
//        )
//    }
//}
