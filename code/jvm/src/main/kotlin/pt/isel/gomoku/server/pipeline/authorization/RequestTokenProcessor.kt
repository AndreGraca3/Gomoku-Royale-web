package pt.isel.gomoku.server.pipeline.authorization

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.repository.dto.AuthenticatedUser
import pt.isel.gomoku.server.service.UserService

@Component
class RequestTokenProcessor(val userService: UserService) {
    fun processAuthorizationHeaderValue(authorizationValue: String?): AuthenticatedUser? {
        if (authorizationValue == null) {
            return null
        }
        return userService.getUserByTokenValue(authorizationValue)
    }
}