package pt.isel.gomoku.server.pipeline.authorization

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.repository.dto.AuthenticatedUser
import pt.isel.gomoku.server.service.UserService

@Component
class RequestTokenProcessor(val userService: UserService) {
    fun processAuthorizationHeaderValue(authorizationValue: String?): AuthenticatedUser? {
        return authorizationValue?.let { userService.getUserByTokenValue(it) }
    }
}