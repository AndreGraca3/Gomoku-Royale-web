package pt.isel.gomoku.server.http.response.problem

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pt.isel.gomoku.server.http.response.Response

@JsonInclude(JsonInclude.Include.NON_NULL)
open class Problem(
    subType: String,
    val status: Int,
    val title: String,
    val detail: String? = null,
    val data: Any? = null,
) : Response() {

    val type = "https://gomokuroyale.pt/probs/$subType"

    companion object {
        const val MEDIA_TYPE = "application/problem+json"
    }

    override fun getContentType() = MEDIA_TYPE

    /**
     * Converts this problem to a [ResponseEntity] already with the correct status code and content type.
     */
    fun toResponseEntity(): ResponseEntity<Any> = ResponseEntity
        .status(status)
        .contentType(MediaType.parseMediaType(this.getContentType()))
        .body(this)
}