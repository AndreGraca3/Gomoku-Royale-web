package pt.isel.gomoku.server.http.model.problem

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
open class Problem(
    subType: String,
    val status: Int,
    val title: String,
    val detail: String? = null,
    val data: Any? = null
) {

    val type = "https://gomokuroyale.pt/probs/$subType"

    companion object {
        val MEDIA_TYPE = MediaType.parseMediaType("application/problem+json")
    }

    fun response() = ResponseEntity
        .status(status)
        .contentType(MEDIA_TYPE)
        .body<Any>(this)
}