package pt.isel.gomoku.server.http.response

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

abstract class Response {
    @JsonIgnore
    abstract fun getContentType(): String

    open fun toResponseEntity(status: Int) = ResponseEntity
        .status(status)
        .contentType(MediaType.parseMediaType(this.getContentType()))
        .body(this)
}