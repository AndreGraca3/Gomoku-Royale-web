package pt.isel.gomoku.server.pipeline.pagination

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import pt.isel.gomoku.server.http.Uris.Pagination.MAX_LIMIT
import pt.isel.gomoku.server.http.model.PaginationInputs

@Component
class PaginationArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == PaginationInputs::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): PaginationInputs {
        val skip = webRequest.getParameter("skip")?.toInt()?.coerceAtLeast(0) ?: 0
        val limit = webRequest.getParameter("limit")?.toInt()?.coerceIn(0, MAX_LIMIT) ?: MAX_LIMIT

        return PaginationInputs(skip, limit)
    }
}