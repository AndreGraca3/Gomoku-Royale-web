package pt.isel.gomoku.server.Filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class AuthorizationFilter : HttpFilter() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        println("Request Authorization Header started...")
        if ((request as HttpServletRequest).getHeader("authorization") == null)
            throw Exception("Unauthorized Request!")
        chain.doFilter(request, response)
        println("Request Authorization Header finished...")
    }
}