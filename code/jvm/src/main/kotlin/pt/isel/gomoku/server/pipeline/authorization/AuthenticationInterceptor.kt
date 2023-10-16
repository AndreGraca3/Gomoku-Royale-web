package pt.isel.gomoku.server.pipeline.authorization

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.gomoku.server.http.model.user.AuthenticatedUser
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationDetails.Companion.NAME_AUTHORIZATION_HEADER
import pt.isel.gomoku.server.pipeline.authorization.AuthenticationDetails.Companion.NAME_WWW_AUTHENTICATE_HEADER

@Component
class AuthenticationInterceptor(val tokenProcessor: RequestTokenProcessor) :
    HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod && handler.methodParameters.any {
                it.parameterType == AuthenticatedUser::class.java
            }) {
            // enforce authentication

            val user = tokenProcessor
                .processAuthorizationHeaderValue(request.getHeader(NAME_AUTHORIZATION_HEADER))
            return if (user == null) {
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, "bearer")
                false
            } else {
                AuthenticatedUserArgumentResolver.addUserTo(user, request)
                true
            }
        }
        return true
    }


}