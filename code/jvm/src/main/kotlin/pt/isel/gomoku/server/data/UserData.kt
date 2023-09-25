package pt.isel.gomoku.server.data

import org.springframework.stereotype.Component
import pt.isel.gomoku.server.structs.dto.outbound.UserDTO

@Component
class UserData: IUserData {
    override fun addUser(): UserDTO {
        return UserDTO("Best id ever")
    }
}